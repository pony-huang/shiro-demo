package com.ponking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ponking.model.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectPermissionsByUserName(@Param("name")String name);
}
