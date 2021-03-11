package com.yukaiji.kjblog.config;

import java.lang.annotation.*;

/**
 * @description:自定义三级缓存注解
 * 1.ThreadLocal:线程级，不设定过期时间
 * 2.LocalCache:进程级，设定过期时间，惰性删除
 * 3.Redis:分布式，设定过期时间
 * @author:yukaiji
 * @createTime:2021/3/10 2:33 下午
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ObmsCache {

    boolean useThreadLocal() default false;

    boolean useLocalCache() default false;

    boolean useRedis() default false;

    String keyName() default "";

    long expireTime() default -1;



}
