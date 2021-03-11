package com.yukaiji.kjblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @description:
 * @author:yukaiji
 * @createTime:2021/3/11 11:06 上午
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class LocalCacheConfig {

    @Resource
    private CacheProperties cacheProperties;

    @Bean("localCacheClient")
    public LocalCache buildCache() {
        LocalCache localCache = LocalCache.getInstance();
        localCache.setMaxMemory(cacheProperties.getMaxMemory());
        localCache.setMaxPair(cacheProperties.getMaxPair());
        localCache.setFullPolicy(cacheProperties.getFullPolicy());
        localCache.setContextName(cacheProperties.getContextName());
        return localCache;
    }
}

@Data
@Configuration
@ConfigurationProperties(prefix = "cache.local-cache")
class CacheProperties {

    private int maxMemory;
    private int maxPair;
    private String contextName;
    private int fullPolicy;

}
