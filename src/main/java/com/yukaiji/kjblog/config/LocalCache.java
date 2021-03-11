package com.yukaiji.kjblog.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.util.RamUsageEstimator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 如果不需要过期时间，或者所有key能够统一过期时间，建议直接用Spring自带@Cacheable注解
 * <p>
 * 1.单例模式
 * 2.支持设定每个Key过期时间
 * 3.线程安全
 * 4.定期删除策略
 *
 * @description: 自定义本地缓存
 * @author:yukaiji
 * @createTime:2021/3/10 9:28 下午
 */
@Data
public class LocalCache {

    /**
     * 单例实例
     **/
    private static final LocalCache INSTANCE = new LocalCache();
    /**
     * 配置化最大容量
     **/
    private Integer maxPair = 1024;
    /**
     * 配置化最大容量
     **/
    private Integer maxMemory = 10;
    /**
     * 容器满时的处理策略 0: 删除第一个添加的元素  1：跳过，无法添加新的缓存数据
     **/
    private int fullPolicy = 0;
    /**
     * 并发插入和删除锁
     **/
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final ReentrantLock lock = new ReentrantLock();
    /**
     * 本地缓存容器
     **/
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private ConcurrentHashMap<String, LocalCacheModel> cacheMap;

    /**
     * 本地缓存容器名称
     **/
    private String contextName = "default";

    /**
     * 第一个添加的元素
     **/
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private volatile LocalCacheModel first;
    /**
     * 最后一个添加的元素
     **/
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private volatile LocalCacheModel last;
    /**
     * 惰性删除记录上次循环的地点
     **/
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private volatile LocalCacheModel loopLastNode;
    /**
     * 链表长度
     **/
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final AtomicInteger linkedSize = new AtomicInteger(0);

    /**
     * 不允许外部实例化
     **/

    private LocalCache() {
        init();
    }

    /**
     * 获取本地缓存实例对象
     **/
    public static LocalCache getInstance() {
        return INSTANCE;
    }

    /**
     * 缓存实例对象模型
     **/
    @Data
    static class LocalCacheModel {

        private String key;

        private Object value;

        private long timeout;

        private LocalCacheModel prev;

        private LocalCacheModel next;

        public LocalCacheModel(String key, Object value, long timeout, LocalCacheModel prev, LocalCacheModel next) {
            this.key = key;
            this.value = value;
            this.timeout = timeout;
            this.prev = prev;
            this.next = next;
        }

    }

    /**
     * 初始化容器和变量相关
     **/
    private void init() {
        this.cacheMap = new ConcurrentHashMap<>(this.maxPair);
        LocalCacheModel finalModel = new LocalCacheModel(UUID.randomUUID().toString(), UUID.randomUUID().toString(), -1, null, null);
        this.first = finalModel;
        this.last = finalModel;
        this.loopLastNode = first;
        this.loopLastNode = first;
        deleteExpireObject();
    }

    /**
     * 惰性删除Scheduled
     **/
    private void deleteExpireObject() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            int i = 1000;
            while (i > 0) {
                i--;
                if (loopLastNode == null) {
                    loopLastNode = first;
                    break;
                }
                if (-1 != loopLastNode.getTimeout() && System.currentTimeMillis() >= loopLastNode.getTimeout()) {
                    LocalCacheModel next = loopLastNode.next;
                    removeNode(loopLastNode);
                    loopLastNode = next;
                    continue;
                }
                loopLastNode = loopLastNode.next;
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取已经使用的内存情况
     *
     * @return 占用内存单位KB
     */
    public long getUseMemory() {
        long memory = (RamUsageEstimator.sizeOfObject(cacheMap) - 64) / 1024;
        System.out.println(contextName + ":当前本地缓存大小存储数量为" + cacheMap.size() + "占用内存：" + memory + "KB");
        return memory;
    }

    /**
     * 插入缓存
     **/
    public void put(String key, Object value, long expireTime) {
        lock.lock();
        try {
            if (cacheMap.size() >= maxPair) {
                if (fullPolicy == 0) {
                    removeFirst();
                } else {
                    return;
                }
            }
            long timeOut = expireTime == -1 ? -1 : expireTime + System.currentTimeMillis();

            LocalCacheModel existModel = cacheMap.get(key);
            if (existModel != null) {
                removeNode(existModel);
            }

            LocalCacheModel cacheModel = new LocalCacheModel(key, value, timeOut, last, null);
            cacheMap.put(key, cacheModel);
            last.next = cacheModel;
            last = cacheModel;
            linkedSize.incrementAndGet();
            getUseMemory();
        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取缓存
     **/
    public Object get(String key) {
        LocalCacheModel localCacheModel = cacheMap.get(key);
        if (null == localCacheModel) {
            return null;
        }
        long expireTime = localCacheModel.getTimeout();
        if (expireTime > 0 && System.currentTimeMillis() > localCacheModel.getTimeout()) {
            removeNode(localCacheModel);
            return null;
        }
        return localCacheModel.getValue();
    }

    /**
     * 删除缓存
     **/
    public void remove(String key) {
        removeNode(cacheMap.get(key));
    }

    /**
     * 删除最早添加的缓存
     **/
    private void removeFirst() {
        removeNode(first.next);
    }

    /**
     * 删除指定的节点
     **/
    private void removeNode(LocalCacheModel localCacheModel) {
        lock.lock();
        try {
            if (localCacheModel != null) {
                cacheMap.remove(localCacheModel.getKey());
                LocalCacheModel next = localCacheModel.next;
                LocalCacheModel prev = localCacheModel.prev;
                if (next != null) {
                    next.prev = prev;
                }
                prev.next = next;
                linkedSize.decrementAndGet();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }
    }
}
