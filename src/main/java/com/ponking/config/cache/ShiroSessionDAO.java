package com.ponking.config.cache;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import java.io.Serializable;

/**
 * @author Peng
 * @date 2020/6/29--15:11
 **/
@Deprecated
public class ShiroSessionDAO extends CachingSessionDAO {


    /**
     * 注入自定义缓存器
     * @param cacheManager
     */
    public ShiroSessionDAO(CacheManager cacheManager) {
        setCacheManager(cacheManager);
    }


    @Override
    protected void doUpdate(Session session) {

    }

    @Override
    protected void doDelete(Session session) {

    }

    @Override
    protected Serializable doCreate(Session session) {
        return null;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return null;
    }
}
