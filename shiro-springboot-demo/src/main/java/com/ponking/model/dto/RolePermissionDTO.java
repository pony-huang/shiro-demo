package com.ponking.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Peng
 * @date 2020/6/26--22:54
 **/
@Data
public class RolePermissionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String pid;

    private String name;

    private List<RolePermissionDTO> children;
}
