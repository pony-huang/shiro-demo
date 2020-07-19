package com.ponking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ponking.mapper.UserMapper;
import com.ponking.model.entity.Role;
import com.ponking.model.entity.User;
import com.ponking.model.entity.UserRole;
import com.ponking.service.RoleService;
import com.ponking.service.UserRoleService;
import com.ponking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;



    @Override
    public User getById(Serializable id) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.select("id", "role_id", "user_id");
        wrapper.eq("user_id", id);
        List<String> roleIds = userRoleService.list(wrapper).stream().map(UserRole::getRoleId)
                .collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        if (roleIds.size() > 0) {
            roleList = roleService.listByIds(roleIds);
        }

        User user = baseMapper.selectById(id);
        user.setRoles(roleList);

        return user;
    }


}
