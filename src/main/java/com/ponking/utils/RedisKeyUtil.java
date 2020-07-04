package com.ponking.utils;

/**
 * @author Peng
 * @date 2020/7/1--22:57
 **/
public class RedisKeyUtil {

    public static String getKey(String cacheName,String key){
        StringBuffer buffer = new StringBuffer();
        buffer.append(cacheName).append(":").append(key);
        return buffer.toString();
    }
}
