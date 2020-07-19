package com.ponking.utils;

import com.ponking.model.params.LoginParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * 登录工具
 * @author Peng
 * @date 2020/6/26--10:20
 **/
public class LoginUtil {

    public static void login(LoginParam loginParam){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new
                UsernamePasswordToken(loginParam.getUsername(),loginParam.getPassword());
        subject.login(token);
    }

    public static void logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    public static boolean isLogin(LoginParam loginParam){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new
                UsernamePasswordToken(loginParam.getUsername(),loginParam.getPassword());
        subject.isAuthenticated();
        subject.login(token);
        return true;
    }
}
