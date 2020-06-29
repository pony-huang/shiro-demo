package com.ponking.model.params;

import com.baomidou.mybatisplus.annotation.*;
import com.ponking.model.base.Converter;
import com.ponking.model.dto.RoleDTO;
import com.ponking.model.entity.Permission;
import com.ponking.model.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Peng
 * @date 2020/6/26--23:49
 **/
@Data
public class RoleParam implements Converter<Role>, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleName;

    private String description;

    private List<String> permissions;

    @Override
    public Role convertTo() {
        Role role = new Role();
        BeanUtils.copyProperties(this,role);
        return role;
    }
}
