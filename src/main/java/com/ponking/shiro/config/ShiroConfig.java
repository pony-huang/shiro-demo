package com.ponking.shiro.config;

import com.ponking.config.cache.ShiroCacheManager;
import com.ponking.filter.AuthTokenFilter;
import com.ponking.shiro.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;


/**
 * @author Peng
 * @date 2020/6/25--16:03
 **/
@Configuration
public class ShiroConfig {

    @Bean(name = "authRealm")
    public UserRealm realm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        userRealm.setCacheManager(cacheManager());
        return userRealm;

    }

    /**
     * 添加缓存
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();
        return cacheManager;
    }

//    @Autowired
//    private org.springframework.cache.CacheManager springCacheManager;
//
//    @Bean
//    public CacheManager cacheManager(){
//        ShiroCacheManager<Object, Object> cacheManager =
//                new ShiroCacheManager<>(springCacheManager);
//        return cacheManager;
//    }

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
    public SecurityManager securityManager(@Qualifier("authRealm") UserRealm authRealm) {
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {

        //当运行一个Web应用程序时,Shiro将会创建一些有用的默认Filter实例,并自动地在[main]项中将它们置为可用自动地
        //可用的默认的Filter实例是被DefaultFilter枚举类定义的,枚举的名称字段就是可供配置的名称

        /**
         * anon---------------org.apache.shiro.web.filter.authc.AnonymousFilter 没有参数，表示可以匿名使用。
         * authc--------------org.apache.shiro.web.filter.authc.FormAuthenticationFilter 表示需要认证(登录)才能使用，没有参数
         * authcBasic---------org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter 没有参数表示httpBasic认证
         * logout-------------org.apache.shiro.web.filter.authc.LogoutFilter
         * noSessionCreation--org.apache.shiro.web.filter.session.NoSessionCreationFilter
         * perms--------------org.apache.shiro.web.filter.authz.PermissionAuthorizationFilter 参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
         * port---------------org.apache.shiro.web.filter.authz.PortFilter port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
         * rest---------------org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter 根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
         * roles--------------org.apache.shiro.web.filter.authz.RolesAuthorizationFilter 参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
         * ssl----------------org.apache.shiro.web.filter.authz.SslFilter 没有参数，表示安全的url请求，协议为https
         * user---------------org.apache.shiro.web.filter.authz.UserFilter 没有参数表示必须存在用户，当登入操作时不做检查
         */

        /**
         * 通常可将这些过滤器分为两组
         * anon,authc,authcBasic,user是第一组认证过滤器
         * perms,port,rest,roles,ssl是第二组授权过滤器
         * 注意user和authc不同：当应用开启了rememberMe时,用户下次访问时可以是一个user,但绝不会是authc,因为authc是需要重新认证的
         * user表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,
         * 比如rememberMe 说白了,以前的一个用户登录时开启了rememberMe,然后他关闭浏览器,下次再访问时他就是一个user,而不会authc
         *
         * 举几个例子
         *  /admin=authc,roles[admin]      表示用户必需已通过认证,并拥有admin角色才可以正常发起'/admin'请求
         *  /edit=authc,perms[admin:edit]  表示用户必需已通过认证,并拥有admin:edit权限才可以正常发起'/edit'请求
         *  /home=user     表示用户不一定需要已经通过认证,只需要曾经被Shiro记住过登录状态就可以正常发起'/home'请求
         */


        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new AuthTokenFilter());
        shiroFilterFactoryBean.setFilters(filters);

        //用LinkedHashMap添加拦截的uri,其中authc指定需要认证的uri，anon指定排除认证的uri
        //各默认过滤器常用如下(注意URL Pattern里用到的是两颗星,这样才能实现任意层次的全匹配)
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/api/admin/login", "anon");
        filterChainDefinitionMap.put("/api/admin/error", "anon");
        filterChainDefinitionMap.put("/api/admin/index", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html/**", "authc");
        filterChainDefinitionMap.put("/api/**", "authc");


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
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
        bean.setSecurityManager(securityManager);
        return bean;
    }

}
