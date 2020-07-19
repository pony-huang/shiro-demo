package com.ponking.service;

import com.ponking.model.dto.PermissionDTO;
import com.ponking.model.entity.Permission;
import com.ponking.model.params.PermissionParam;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
public interface PermissionService extends BaseService<PermissionDTO,Permission> {
    /**
     * 根据用户名获取权限属性
     * @param name
     * @return
     */
    List<String> listPermissionByUserName(String name);


    /**
     * 根据角色id获取权限属性
     * @param id
     * @return
     */
    List<String> listPermissionByRoleId(String id);

    void createBy(PermissionParam permissionParam);
}
