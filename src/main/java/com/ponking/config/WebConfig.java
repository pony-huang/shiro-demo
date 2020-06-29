package com.ponking.config;

import com.ponking.handler.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Peng
 * @date 2020/6/25--13:54
 **/
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor())
                .excludePathPatterns("/api/admin/login")
                .excludePathPatterns("/api/admin/logout")
                .addPathPatterns("/api/admin/**");
    }
}
