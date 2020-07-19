package com.ponking.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Peng
 * @date 2020/6/26--18:03
 **/
@Data
public class UserVo implements Serializable {
    private final static long serialVersionUID = 1L;

    private String id;
    private String username;
    private String nickName;
    private List<RoleVo> roles;
}
