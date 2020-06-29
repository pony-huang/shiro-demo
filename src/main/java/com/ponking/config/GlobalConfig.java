package com.ponking.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author Peng
 * @date 2020/6/26--10:55
 **/
@Configuration
@MapperScan("com.ponking.mapper")
@ComponentScan(basePackages = {"com.ponking.utils","com.ponking.handler"})
public class GlobalConfig {
}
