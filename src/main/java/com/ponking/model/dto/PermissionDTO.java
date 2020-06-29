package com.ponking.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Peng
 * @date 2020/6/27--9:05
 **/
@Data
public class PermissionDTO implements Serializable {

    private final static long serialVersionID = 1L;

    private String id;

    private String pid;

    private String name;

    private Integer type;

    private String permissionValue;

    private String path;

    private String component;

    private String icon;

    private Integer status;

    private List<PermissionDTO> children;

    public PermissionDTO(){
        this(new ArrayList<>());
    }

    private PermissionDTO(List<PermissionDTO> children) {
        this.children = children;
    }
}
