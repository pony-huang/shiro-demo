package com.ponking.aop;

import com.ponking.model.entity.Log;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import netscape.security.Privilege;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Peng
 * @date 2020/7/11--10:42
 **/
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.ponking.aop.annotation.Log)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);

        String uri = request.getRequestURI();
        String host = request.getRemoteAddr();
        String device = getDevice(request.getHeader("User-Agent"));
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        String op = annotation.value();



        if(username == null ){
            // 注意:登录时候,先走自定义aop,再走shiro登录操作,故 uername第一次登录时候未null,给它通过
           return pjp.proceed();
        }

        Log log = new Log();
        log.setDevice(device);
        log.setHost(host);
        log.setOperation(op);
        log.setUri(uri);
        log.setUsername(username);

        LogAspect.log.info("执行了 [{}] 操作", annotation.value());
        return pjp.proceed();
    }


    private String getDevice(String userAgent){
        StringBuilder builder = new StringBuilder();

        //device
        if(userAgent.contains("Windows")){
            builder.append("Windows");
        }else if(userAgent.contains("Linux")){
            builder.append("Linux");
        }else if(userAgent.contains("Android")){
            builder.append("Android");
        }else if(userAgent.contains("iPhone")){
            builder.append("iPhone");
        }else if(userAgent.contains("iPad")){
            builder.append("iPad");
        }else{
            builder.append("unknown");
        }

        builder.append(";");

        //Browser
        if(userAgent.contains("Firefox")){
            builder.append("Firefox");
        }else if(userAgent.contains("Firefox")){
            builder.append("Linux");
        }else if(userAgent.contains("Explorer")){
            builder.append("Internet Explorer");
        }else if(userAgent.contains("Chrome")){
            builder.append("Chrome");
        }else if(userAgent.contains("Opera")){
            builder.append("Opera");
        }

        return builder.toString();

    }
}
