package com.ponking.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @author Peng
 * @date 2020/6/29--13:42
 **/
@Deprecated
public class ShiroCacheManager<K,V> implements CacheManager {

    /**
     * Spring的缓存管理器
     */
    private org.springframework.cache.CacheManager springCacheManager;


    public ShiroCacheManager(org.springframework.cache.CacheManager springCacheManager) {
        this.springCacheManager = springCacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        return new ShiroSpringCache<>(springCacheManager,cacheName);
    }
}
