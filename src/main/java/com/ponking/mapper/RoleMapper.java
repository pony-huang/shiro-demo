package com.ponking.mapper;

import com.ponking.model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRolesByUserName(@Param("name")String name);
}
