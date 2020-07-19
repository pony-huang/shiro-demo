package com.ponking.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Peng
 * @date 2020/7/1--22:18
 **/
public class RedisCacheManager implements CacheManager {


    private RedisTemplate redisTemplate;

    public RedisCacheManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        return new RedisCache(cacheName,redisTemplate);
    }
}
