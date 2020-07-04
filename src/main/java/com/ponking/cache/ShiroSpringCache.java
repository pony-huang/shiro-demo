package com.ponking.cache;

import org.apache.shiro.cache.CacheException;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Springboot缓存整合shiro缓存
 * @author Peng
 * @date 2020/6/29--14:50
 **/
@Deprecated
public class ShiroSpringCache<K,V> implements org.apache.shiro.cache.Cache<K,V> {

    /**
     * Spring的缓存管理器
     */
    private CacheManager springCacheManager;


    /**
     * Spring的缓存对象
     */
    private org.springframework.cache.Cache springCache;

    public ShiroSpringCache(CacheManager springCacheManager, String cacheName) {
        this.springCacheManager = springCacheManager;
        this.springCache = this.springCacheManager.getCache(cacheName);

    }

    @Override
    public V get(K key) throws CacheException {
        org.springframework.cache.Cache.ValueWrapper valueWrapper = springCache.get(key);
        if (valueWrapper == null) {
            return null;
        }
        return (V) valueWrapper.get();
    }

    @Override
    public V put(K k, V v) throws CacheException {
        springCache.put(k,v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V value = this.get(k);
        springCache.evict(k);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        springCache.clear();
    }

    @Override
    public int size() {
        return this.keys().size();
    }

    @Override
    public Set<K> keys() {
        return (Set) springCacheManager.getCacheNames();
    }

    @Override
    public Collection values() {
        return this.keys().stream()
                .map(this::get)
                .collect(Collectors.toList());
    }
}
