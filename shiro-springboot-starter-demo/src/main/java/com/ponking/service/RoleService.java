package com.ponking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ponking.model.entity.Role;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
public interface RoleService extends IService<Role> {

    List<String> listRolesByUserName(String name);

}
