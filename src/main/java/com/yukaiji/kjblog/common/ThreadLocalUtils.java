package com.yukaiji.kjblog.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @description: ThreadLocal工具类
 * @author:yukaiji
 * @createTime:2021/3/10 3:30 下午
 */
public class ThreadLocalUtils {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(() -> new HashMap<>(16));

    public static Map<String, Object> getThreadLocal() {
        return THREAD_LOCAL.get();
    }

    public static <T> T get(String key) {
        return get(key, null);
    }

    public static <T> T getAndConvert(String key, TypeReference<T> typeReference) {
        Map<String, Object> map = THREAD_LOCAL.get();
        Object object = map.get(key);
        return JSON.parseObject(object.toString(), typeReference);
    }

    public static <T> void set(String key, T value, TypeReference<T> typeReference) {
        Map<String, Object> map = THREAD_LOCAL.get();
        map.put(key, JSON.toJSONString(value));
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key, T defaultValue) {
        Map<String, Object> map = THREAD_LOCAL.get();
        return (T) Optional.ofNullable(map.get(key)).orElse(defaultValue);
    }

    public static void set(String key, Object value) {
        Map<String, Object> map = THREAD_LOCAL.get();
        map.put(key, value);
    }

    public static void set(Map<String, Object> keyValueMap) {
        Map<String, Object> map = THREAD_LOCAL.get();
        map.putAll(keyValueMap);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    @SuppressWarnings("unchecked")
    public static <T> T remove(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        return (T) map.remove(key);
    }

    public static Map<String, Object> getAll(){
        return THREAD_LOCAL.get();
    }

}
