package com.ponking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.model.dto.RoleDTO;
import com.ponking.model.dto.UserDTO;
import com.ponking.model.entity.Role;
import com.ponking.model.entity.User;
import com.ponking.mapper.UserMapper;
import com.ponking.model.entity.UserRole;
import com.ponking.model.params.UserParam;
import com.ponking.model.params.UserQuery;
import com.ponking.model.vo.UserListVo;
import com.ponking.model.vo.UserVo;
import com.ponking.service.BaseService;
import com.ponking.service.RoleService;
import com.ponking.service.UserRoleService;
import com.ponking.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ponking.utils.RandomUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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

    private static final String INSERT_OP = "insert";

    private static final String UPDATE_OP = "update";

    @Override
    public UserListVo fetchList(UserQuery query) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username", "nick_name", "gmt_create", "gmt_modified");
        wrapper.orderByDesc("gmt_modified");
        Page<User> page = new Page<>(query.getPage(), query.getLimit());
        baseMapper.selectPage(page, wrapper);
        long total = page.getTotal();
        List<UserVo> users = page.getRecords().stream()
                .map(user -> {
                    UserVo vo = new UserVo();
                    BeanUtils.copyProperties(user, vo);
                    return vo;
                }).collect(Collectors.toList());

        return new UserListVo().setTotal(total).setUsers(users);
    }

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserParam user) {
        Assert.notNull(user, "param is null");

        User createUser = user.convertTo();
        String salt = RandomUtil.createSalt();
        createUser.setSalt(salt);
        createUser.setPassword(RandomUtil.encryptByMd5(user.getPassword(), salt));
        this.save(createUser);
        user.setId(createUser.getId());

        if (user.getRoles().size() > 0) {
            try {
                assignRoles(user.getId(), user.getRoles(), INSERT_OP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateById(UserParam user) {
        Assert.notNull(user, "user is null");
        Assert.notNull(user.getId(), "user.id is null");
        Assert.notNull(user.getPassword(), "user.password is null");
        isPwdRight(user);

        if (user.getRoles().size() > 0) {
            try {
                assignRoles(user.getId(), user.getRoles(), UPDATE_OP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.updateById(user.convertTo());
        return true;
    }

    /**
     * 判断原始密码是否正确
     * 如果SuperAdmin,能修改任何用户密码,不需要填写原始密码,直接修该(Super Admin)
     * @param user
     */
    @RequiresPermissions("user:assign")
    public void isPwdRight(UserParam user){
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("Super Admin")) {
            User dbUser = baseMapper.selectById(user.getId());
            String test = RandomUtil.encryptByMd5(user.getOriginPassword(), dbUser.getSalt());
            if (!dbUser.getPassword().equals(test)) {
                throw new RuntimeException("原始密码错误");
            }
            String salt = dbUser.getSalt();
            if (StringUtils.isEmpty(salt)) {
                salt = RandomUtil.createSalt();
            }
            user.setPassword(RandomUtil.encryptByMd5(user.getPassword(), salt));
        }
    }

    /**
     * 分配角色(需要权限user:assign)
     *
     * @param userId
     * @param roleIds
     * @param op
     */
    @RequiresPermissions("user:assign")
    private void assignRoles(String userId, List<String> roleIds, String op) {

        // 若更新操作,先删除旧数据，在插入新数据达到更新目的
        if (op.equals(UserServiceImpl.UPDATE_OP)) {
            QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            userRoleService.remove(wrapper);
        }

        List<UserRole> userRoles = roleIds.stream().map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            return userRole;
        }).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
    }

    /**
     * 更新或者插入操作
     *
     * @param user
     * @param op
     */
    @Deprecated
    private void insertOrUpdateUserRole(UserParam user, String op) {
        if (op.equals(UserServiceImpl.UPDATE_OP)) {
            QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", user.getId());
            userRoleService.remove(wrapper);
        }
        if (user.getRoles().size() > 0) {
            //先删除旧数据，在插入新数据达到更新目的
            List<UserRole> userRoles = user.getRoles().stream().map(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(user.getId());
                return userRole;
            }).collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
        }
    }

    @Override
    public UserDTO convertTo(User source) {
        Assert.notNull(source, "param is null");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(source, userDTO);
        if (source.getRoles().size() > 0) {
            List<String> roles = source.getRoles().stream()
                    .map(Role::getId).collect(Collectors.toList());
            userDTO.setRoles(roles);
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> convertTo(List<User> sources) {
        return sources.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(user, userDTO);
                    return userDTO;
                }).collect(Collectors.toList());
    }
}
