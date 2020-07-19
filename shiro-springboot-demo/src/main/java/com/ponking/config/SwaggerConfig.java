package com.ponking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Peng
 * @date 2020/6/25--11:10
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket haloDefaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ponking.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Halo API Documentation")
                .description("Documentation for Shiro-demo API")
                .version("version 1.0.0")
                .termsOfServiceUrl("https://www.github.com")
                .contact(new Contact("halo-dev", "https://www.github.com", "i#ryanc.cc"))
                .license("GNU General Public License v3.0")
                .licenseUrl("https://www.github.com")
                .build();
    }
}
