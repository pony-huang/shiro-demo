package com.ponking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ponking.mapper.UserRoleMapper;
import com.ponking.model.entity.UserRole;
import com.ponking.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
