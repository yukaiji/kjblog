package com.yukaiji.kjblog.service;

import com.yukaiji.kjblog.config.LocalCache;
import com.yukaiji.kjblog.config.ObmsCache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @description:
 * @author:yukaiji
 * @createTime:2021/3/10 5:12 下午
 */
@Service
public class CacheTestService {

    @Resource
    private LocalCache localClient;

    @ObmsCache(keyName = "#key + '_' + #key2", useThreadLocal = true)
    public String obmsCacheThreadLocalTest(String key, String key2) throws InterruptedException {
        Thread.sleep(3000);
        return key;
    }

    public String localCacheTest(String key) throws InterruptedException {
        Object obj = localClient.get(key);
        if (obj != null) {
            System.out.println("缓存生效了" + key);
            return obj.toString();
        }
        Random random = new Random();
        Thread.sleep(2000);
        localClient.put(key, key , 5000 + random.nextInt(50000));
        return key;
    }

    @Cacheable(key = "#key", cacheManager = "caffeineCacheManager", cacheNames = "oneName")
    public String caffeineCacheTest(String key) throws InterruptedException {
        Thread.sleep(2000);
        return key;
    }

    @Cacheable(key = "#key", cacheManager = "caffeineCacheManager", cacheNames = "twoName")
    public String caffeineCacheTest2(String key) throws InterruptedException {
        Thread.sleep(2000);
        return key;
    }

}
