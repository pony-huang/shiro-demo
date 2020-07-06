package com.ponking.handler;

import com.ponking.constant.AuthorizationConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Peng
 * @date 2020/6/28--23:44
 **/
@Slf4j
@Deprecated
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public final static String TOKEN_NAME = "resubmitToken";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Annotation[] annotations = method.getAnnotations();
        log.info(Arrays.toString(annotations));
//        System.out.println(Arr);
        //从header中得到token
        String token = request.getHeader(AuthorizationConstant.HEADER_TOKEN_NAME);
        //System.out.println("authorization = " + authorization);
        //验证token
//        JwtUtil.parseJWT(token,audience.getBase64Secret());

        //如果验证token失败，并且方法注明了Authorization，返回401错误
//        if (method.getAnnotation(Authorization.class) != null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
        return true;

    }
}
