package com.yukaiji.kjblog.common;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** 
 * @description:Redis工具类
 * @author:yukaiji
 * @createTime:2021/3/10 4:49 下午
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key      缓存的健值
     * @param value    缓存的值
     * @return         缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        return operations;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key      缓存的健值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @return         缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Long timeout, TimeUnit timeUnit) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        operations.set(key, value, timeout, timeUnit);
        return operations;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 设置过期时间
     * @param key           key值
     * @param timeout       时间
     * @param timeUnit      时间单位
     * @return
     */
    public Boolean setExpire(String key, Long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 删除单个对象
     * @param key
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     * @param collection
     */
    public void deleteObject(Collection collection) {
        redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return         缓存的对象
     */
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperations = redisTemplate.opsForList();
        if (null != listOperations) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperations.leftPush(key, dataList.get(i));
            }
        }
        return listOperations;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<>();
        ListOperations<String, T> listOperations = redisTemplate.opsForList();
        Long size = listOperations.size(key);
        for (long i = 0; i < size; i++) {
            dataList.add(listOperations.index(key, i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
        Iterator<T> iterable = dataSet.iterator();
        while (iterable.hasNext()) {
            setOperations.add(iterable.next());
        }
        return setOperations;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet<>();
        BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
        Long size = setOperations.size();
        for (long i = 0; i < size; i++) {
            dataSet.add(setOperations.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public void hashPutAll(String key, Map<String, Object> dataMap) {
        redisTemplate.opsForHash().putAll(key, dataMap);
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     */
    public Map<String, Object> hashGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取键值为key的hash中的field字段的值
     * @param key
     * @param field
     */
    public Object hashGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取键值为key的hash表中所有字段
     * @param key
     */
    public Set<String> hashKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取键值为key的hash表中所有value
     * @param key
     */
    public List<Object> hashValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 判断hashKey是否存在
     * @param key
     * @param hashKey
     * @return 存在返回true，不存在返回false
     */
    public Boolean hasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 自增
     * @param key
     * @return     自增后的值
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 自增 num
     * @param key
     * @return   自增后的值
     */
    public Long increment(String key, long num) {
        return redisTemplate.opsForValue().increment(key, num);
    }

    /**
     * 执行lua脚本，返回执行结果
     * @param redisScript
     * @param key
     * @param value
     * @return
     */
    public Object execute(DefaultRedisScript redisScript, String key, Object value) {
        return redisTemplate.execute(redisScript, Arrays.asList(key), value);
    }

}
