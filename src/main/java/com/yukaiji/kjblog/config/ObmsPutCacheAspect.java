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
 * @description:自定义三级缓存注解，用于数据更新后的缓存更新操作。
 * @author:yukaiji
 * @createTime:2021/3/10 5:38 下午
 */
@Aspect
@Component
public class ObmsPutCacheAspect {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     */
    @Pointcut("@annotation(ObmsPutCache)")
    public void putCacheAspect() {
    }

    @Around("putCacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ObmsPutCache putCache = this.getMethodAnnotation(joinPoint);
        // check switch
        if (!checkAnnotationParam(putCache)) {
            return joinPoint.proceed();
        }
        return refreshCache(joinPoint, putCache);
    }

    private Object refreshCache(ProceedingJoinPoint joinPoint, ObmsPutCache obmsPutCache) throws Throwable {
        Object object = joinPoint.proceed();
        try {
            // 不传Key默认生成
            String cacheKeyName = obmsPutCache.keyName();
            System.out.println("cacheKeyName = " + cacheKeyName);
            if (StringUtils.isEmpty(cacheKeyName)) {
                cacheKeyName = genDefaultKey(joinPoint, obmsPutCache);
            }
            // 直接覆盖
            if (obmsPutCache.useThreadLocal()) {
                ThreadLocalUtils.set(cacheKeyName, object);
            }

            if (obmsPutCache.useEhCache()) {
                //            ThreadLocalUtils.set(cacheKeyName, object);
            }

            // 更新Redis缓存
            if (obmsPutCache.useRedis()) {
                redisUtil.deleteObject(cacheKeyName);
                redisUtil.setCacheObject(cacheKeyName, object, obmsPutCache.expireTime(), TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            System.out.println("");
        }
        return object;
    }

    /**
     * 校验注解参数
     *
     * @param obmsCache 注解
     * @return true false
     */
    private boolean checkAnnotationParam(ObmsPutCache obmsCache) {
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
    private String genDefaultKey(ProceedingJoinPoint joinPoint, ObmsPutCache obmsCache) {
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
    private ObmsPutCache getMethodAnnotation(JoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        return objMethod.getDeclaredAnnotation(ObmsPutCache.class);
    }


}
