package com.ponking.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 * @date 2020/6/27--0:37
 **/
@Data
public class RoleVo {

    private String id;

    private String roleName;

    private String description;

    private List<String> permissions;

}
