package com.ponking.model.params;

import lombok.Data;

/**
 * @author Peng
 * @date 2020/7/6--21:09
 **/
@Data
public class RegisterUserParam {

    private String username;

    private String password;

    private String nickName;
}
