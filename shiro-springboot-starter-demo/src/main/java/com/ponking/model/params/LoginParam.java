package com.ponking.model.params;

import lombok.Data;

/**
 * @author Peng
 * @date 2020/6/25--14:36
 **/
@Data
public class LoginParam {
    private String username;
    private String password;
    /**
     * 认证码
     */
    private String authcode;
}
