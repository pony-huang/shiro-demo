package com.ponking.cache;

import lombok.SneakyThrows;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * @author Peng
 * @date 2020/7/1--22:18
 **/
public class RedisCache<K, V> implements Cache<K, V> {


    private String cacheName;

    private final String SHIRO_CACHE_SUFFIX;

    private RedisTemplate redisTemplate;


    public RedisCache(String cacheName, RedisTemplate redisTemplate) {
        this.cacheName = cacheName;
        this.redisTemplate = redisTemplate;
        this.SHIRO_CACHE_SUFFIX = this.cacheName + ":shiro_user:";
    }

    @SneakyThrows
    @Override
    public V get(K k) throws CacheException {
        SimplePrincipalCollection principal = (SimplePrincipalCollection)k;
        Object key = principal.getPrimaryPrincipal();
        return (V) redisTemplate.opsForValue().get(SHIRO_CACHE_SUFFIX+key);
    }

    @SneakyThrows
    @Override
    public V put(K k, V value) throws CacheException {
        SimplePrincipalCollection principal = (SimplePrincipalCollection)k;
        Object key = principal.getPrimaryPrincipal();
        redisTemplate.opsForValue().set(SHIRO_CACHE_SUFFIX+key, value);
        return value;
    }

    @Override
    public V remove(K k) throws CacheException {
        SimplePrincipalCollection principal = (SimplePrincipalCollection)k;
        Object key = principal.getPrimaryPrincipal();
        V returnValue = (V) redisTemplate.opsForValue().get(key);
        redisTemplate.delete(SHIRO_CACHE_SUFFIX+key);
        return returnValue;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        String pattern = "\\^"+SHIRO_CACHE_SUFFIX+":.*";
        Set<K> keys = (Set<K>) redisTemplate.keys(pattern);
        List<V> objects = redisTemplate.opsForValue().multiGet(keys);
        return objects.size();
    }

    @Override
    public Set<K> keys() {
        String pattern = "\\^"+SHIRO_CACHE_SUFFIX+":.*";
        return (Set<K>) redisTemplate.keys(pattern);
    }

    @Override
    public Collection<V> values() {
        String pattern = "\\^"+SHIRO_CACHE_SUFFIX+":.*";
        Set<K> keys = (Set<K>) redisTemplate.keys(pattern);
        List<V> objects = redisTemplate.opsForValue().multiGet(keys);
        return objects;
    }
}
