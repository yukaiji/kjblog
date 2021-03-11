package com.yukaiji.kjblog.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: caffeine缓存配置
 * @author:yukaiji
 * @createTime:2021/3/11 2:04 下午
 */
@Configuration
@EnableConfigurationProperties(CaffeineCacheProperties.class)
public class CaffeineCacheConfig {

    @Resource
    private CaffeineCacheProperties caffeineCacheProperties;

    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = buildCaffeineCacheList();
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    /**
     * 构建不同配置的缓存容器
     * @return 缓存容器
     */
    private List<CaffeineCache> buildCaffeineCacheList(){

        List<CaffeineCache> caffeineCacheList = new ArrayList<>();
        // 第一个Caffeine缓存容器
        CaffeineCacheProperties.CaffeineCacheModel testOneModel = caffeineCacheProperties.getTestOneCache();
        caffeineCacheList.add(new CaffeineCache(testOneModel.getCacheContextName(),
                Caffeine.newBuilder().recordStats()
                        .expireAfterWrite(testOneModel.getTtl(), TimeUnit.SECONDS)
                        .maximumSize(testOneModel.getMaximumSize())
                        .build()));

        // 第二个Caffeine缓存容器
        CaffeineCacheProperties.CaffeineCacheModel testTwoModel = caffeineCacheProperties.getTestTwoCache();
        caffeineCacheList.add(new CaffeineCache(testTwoModel.getCacheContextName(),
                Caffeine.newBuilder().recordStats()
                        .expireAfterWrite(testTwoModel.getTtl(), TimeUnit.SECONDS)
                        .maximumSize(testTwoModel.getMaximumSize())
                        .build()));
        return caffeineCacheList;
    }


}

@Data
@Configuration
@ConfigurationProperties(prefix = "cache.caffeine")
class CaffeineCacheProperties {

    @NestedConfigurationProperty
    private CaffeineCacheModel testOneCache;
    @NestedConfigurationProperty
    private CaffeineCacheModel testTwoCache;


    @Data
    public static class CaffeineCacheModel {
        private String cacheContextName;
        private long ttl;
        private long maximumSize;
    }

}