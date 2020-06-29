package com.ponking.model.params;

import com.ponking.model.base.Converter;
import com.ponking.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author Peng
 * @date 2020/6/26--20:39
 **/
@Data
public class UserParam implements Converter<User> {

    private String id;
    private String username;
    private String password;
    private String originPassword;
    private String nickName;
    private String avatar;
    private List<String> roles;

    @Override
    public User convertTo() {
        User user = new User();
        BeanUtils.copyProperties(this,user);
        return user;
    }
}
