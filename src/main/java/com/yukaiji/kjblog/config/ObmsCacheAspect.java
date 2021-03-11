package com.yukaiji.kjblog.config;

import com.yukaiji.kjblog.common.RedisUtil;
import com.yukaiji.kjblog.common.ThreadLocalUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @description:自定义三级缓存注解AOP
 * @author:yukaiji
 * @createTime:2021/3/10 2:36 下午
 */
@Aspect
@Component
public class ObmsCacheAspect {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     */
    @Pointcut("@annotation(ObmsCache)")
    public void cacheAspect() {
    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ObmsCache obmsCache = this.getMethodAnnotation(joinPoint);
        // check switch
        if (!checkAnnotationParam(obmsCache)) {
            return joinPoint.proceed();
        }
        // 不传Key默认生成
        String cacheKeyName = obmsCache.keyName();
        System.out.println("cacheKeyName = " + cacheKeyName);
        if (StringUtils.isEmpty(cacheKeyName)) {
            cacheKeyName = genDefaultKey(joinPoint, obmsCache);
        }
        // 逐级获取缓存
        Object object = this.getCache(cacheKeyName, obmsCache);
        // 创建缓存
        if (object == null) {
            object = this.createCache(joinPoint, cacheKeyName, obmsCache);
        }
        return object;
    }

    /**
     * 获取缓存信息
     *
     * @param cacheKeyName 缓存Key
     * @param obmsCache    注解
     * @return 缓存信息
     */
    private Object getCache(String cacheKeyName, ObmsCache obmsCache) {
        try {
            // 一级ThreadLocal
            if (obmsCache.useThreadLocal()) {
                Object result = ThreadLocalUtils.get(cacheKeyName);
                if (result != null) {
                    return result;
                }
            }
            // 二级本地缓存Ehcache
            if (obmsCache.useEhCache()) {
                //            Object result = ThreadLocalUtils.get(cacheKeyName);
                //            if (result!=null) {
                //                return result;
                //            }
            }
            // 三级分布式缓存Redis
            if (obmsCache.useRedis()) {
                Object result = redisUtil.getCacheObject(cacheKeyName);
                if (result != null) {
                    return result;
                }
            }
        } catch (Exception e) {
            System.out.println("getCache fail cacheKey" + cacheKeyName);
        }
        return null;
    }

    /**
     * 创建缓存信息
     *
     * @param joinPoint
     * @param cacheKeyName
     * @param obmsCache
     * @return
     * @throws Throwable
     */
    private Object createCache(ProceedingJoinPoint joinPoint, String cacheKeyName, ObmsCache obmsCache) throws Throwable {
        long expireTime = obmsCache.expireTime();
        // 执行源方法
        Object object = joinPoint.proceed();

        // 设置缓存
        try {
            if (obmsCache.useThreadLocal()) {
                ThreadLocalUtils.set(cacheKeyName, object);
            }
            if (obmsCache.useEhCache()) {
                //            ThreadLocalUtils.set(cacheKeyName, object);
            }
            if (obmsCache.useRedis()) {
                redisUtil.setCacheObject(cacheKeyName, object, expireTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            System.out.println("createCache fail cacheKey" + cacheKeyName);
        }
        return object;
    }

    /**
     * 校验注解参数
     *
     * @param obmsCache 注解
     * @return true false
     */
    private boolean checkAnnotationParam(ObmsCache obmsCache) {
        if (obmsCache.useEhCache() || obmsCache.useRedis() || obmsCache.useThreadLocal()) {
            return true;
        }
        return false;
    }

    /**
     * 根据方法参数生成默认Key
     *
     * @param joinPoint 切点
     * @param obmsCache 注解
     * @return 默认生成的Key
     */
    private String genDefaultKey(ProceedingJoinPoint joinPoint, ObmsCache obmsCache) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();

        String defaultKey = "1111";
        System.out.println("默认生成的key:" + defaultKey);
        return defaultKey;
    }

    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint 切入点
     * @return 注解
     * @throws NoSuchMethodException
     */
    private ObmsCache getMethodAnnotation(JoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        return objMethod.getDeclaredAnnotation(ObmsCache.class);
    }


}
