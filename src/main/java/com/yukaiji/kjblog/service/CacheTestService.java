package com.yukaiji.kjblog.service;

import com.yukaiji.kjblog.config.LocalCache;
import com.yukaiji.kjblog.config.ObmsCache;
import com.yukaiji.kjblog.config.ObmsPutCache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @ObmsCache(keyName = "#key", useLocalCache = true, expireTime = 3000)
    public String obmsCacheLocalTest(String key) throws InterruptedException {
        Thread.sleep(3000);
        return key;
    }

    @ObmsPutCache(keyName = "#key", useLocalCache = true, expireTime = 6000)
    public String obmsCacheLocalPutTest(String key) throws InterruptedException {
        Thread.sleep(3000);
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


    @ObmsCache(keyName = "#key", useRedis = true, expireTime = 30000)
    public List<String> obmsCacheRedisTest(String key) throws InterruptedException {
        List<String> x = new ArrayList<>();
        x.add("1");
        x.add("2");
        x.add("3");
        Thread.sleep(2000);
        return x;
    }

}
