package com.ponking.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Peng
 * @date 2020/6/26--21:32
 **/
@Data
public class RoleDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private String id;

    private String roleName;

    private String roleCode;

    private String description;

    private List<String> permissions;

}
