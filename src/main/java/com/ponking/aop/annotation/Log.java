package com.ponking.aop.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 * @author Peng
 * @date 2020/7/11--10:40
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
}
