package com.ponking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ponking.mapper.PermissionMapper;
import com.ponking.model.entity.Permission;
import com.ponking.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Override
    public List<String> listPermissionByUserName(String name) {
        return baseMapper.selectPermissionsByUserName(name);
    }

    @Override
    public List<String> listPermissionByRoleId(String id) {
        return null;
    }

    @Override
    public void createBy(Permission permissionParam) {

    }
}
