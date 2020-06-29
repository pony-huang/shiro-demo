package com.ponking.service;

import com.ponking.model.dto.RoleDTO;
import com.ponking.model.entity.Role;
import com.ponking.model.params.RoleParam;
import com.ponking.model.params.RoleQuery;
import com.ponking.model.vo.RoleListVo;
import com.ponking.model.vo.RoleVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
public interface RoleService extends BaseService<RoleDTO,Role> {

    List<String> listRolesByUserName(String name);

    RoleListVo fetchList(RoleQuery query);

    List<RoleVo> fetchList();

    void updateById(RoleParam roleParam);

    void create(RoleParam roleParam);

}
