package com.ponking.model.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ponking.model.base.Converter;
import com.ponking.model.entity.Permission;
import com.ponking.model.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author Peng
 * @date 2020/6/27--9:48
 **/
@Data
public class PermissionParam implements Converter<Permission>, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String pid;

    @NonNull
    private String name;

    @NonNull
    private Integer type;

    private String permissionValue;

    private String path;

    private String component;

    private String icon;

    private Integer status;

    @Override
    public Permission convertTo() {
        Permission permission = new Permission();
        BeanUtils.copyProperties(this,permission);
        return permission;
    }

}
