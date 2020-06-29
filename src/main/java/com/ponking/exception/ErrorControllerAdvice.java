package com.ponking.exception;

import com.ponking.model.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕捉处理
 * @author Peng
 * @date 2020/6/29--19:45
 **/
@ControllerAdvice
@RestController
@Slf4j
public class ErrorControllerAdvice {

    /**
     * 权限访问错误
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public Result authorizationErrorHandler(Exception e) {
        e.printStackTrace();
        return Result.failed().message("没有权限访问");
    }


    /**
     * 默认
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception e) {
        e.printStackTrace();
        return Result.failed();
    }

}
