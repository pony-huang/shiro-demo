package com.ponking.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ponking.mapper.RoleMapper;
import com.ponking.model.entity.Role;
import com.ponking.service.PermissionService;
import com.ponking.service.RolePermissionService;
import com.ponking.service.RoleService;
import com.ponking.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<String> listRolesByUserName(String name) {
        return baseMapper.selectRolesByUserName(name);
    }


}
