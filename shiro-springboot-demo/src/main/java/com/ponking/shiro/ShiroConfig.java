package com.ponking.shiro;

import com.ponking.filter.AuthTokenFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author Peng
 * @date 2020/6/25--16:03
 **/
@Configuration
public class ShiroConfig {


    @Bean(name = "authRealm")
    public AuthRealm realm(@Qualifier(value = "cacheManager")CacheManager cacheManager) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        authRealm.setCacheManager(cacheManager);
        return authRealm;

    }



    /**
     * 添加缓存
     * @return
     */
    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
        MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();
        return cacheManager;
    }


    /**
     * 采用无会话token,禁用session
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }


    @Bean(name = "securityManager")
    public org.apache.shiro.mgt.SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm);
        securityManager.setSessionManager(defaultWebSessionManager());
        return securityManager;
    }


    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") org.apache.shiro.mgt.SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authTokenFilter", new AuthTokenFilter());
        filters.put("noSession",new NoSessionCreationFilter());
        shiroFilterFactoryBean.setFilters(filters);


        //用LinkedHashMap添加拦截的uri,其中authc指定需要认证的uri，anon指定排除认证的uri
        //各默认过滤器常用如下(注意URL Pattern里用到的是两颗星,这样才能实现任意层次的全匹配)
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/api/admin/login", "anon");
        filterChainDefinitionMap.put("/api/admin/logout", "anon");
        filterChainDefinitionMap.put("/api/admin/register", "anon");
        filterChainDefinitionMap.put("/api/admin/error", "anon");
        filterChainDefinitionMap.put("/api/admin/index", "anon");
        filterChainDefinitionMap.put("/api/admin/index.html", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html/**", "anon");
        filterChainDefinitionMap.put("/api/admin/**", "authc");


        //设置登录失败，授权成功、授权失败之后的uri
        shiroFilterFactoryBean.setLoginUrl("/api/admin/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/admin/error");
        shiroFilterFactoryBean.setSuccessUrl("/admin/admin/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        return shiroFilterFactoryBean;
    }


    /**
     * 加密
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }


    /**
     * 生命周期维护容器
     * 必须在其他4个容器之后注入。不然会抛异常。
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
        bean.setSecurityManager(securityManager);
        return bean;
    }

}
