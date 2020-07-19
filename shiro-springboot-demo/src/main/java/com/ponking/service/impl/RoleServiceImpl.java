package com.ponking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ponking.mapper.RoleMapper;
import com.ponking.model.dto.RoleDTO;
import com.ponking.model.entity.Permission;
import com.ponking.model.entity.Role;
import com.ponking.model.entity.RolePermission;
import com.ponking.model.params.RoleParam;
import com.ponking.model.params.RoleQuery;
import com.ponking.model.vo.RoleListVo;
import com.ponking.model.vo.RoleVo;
import com.ponking.service.PermissionService;
import com.ponking.service.RolePermissionService;
import com.ponking.service.RoleService;
import com.ponking.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public RoleListVo fetchList(RoleQuery query) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("id", "role_name", "description");
        Page<Role> page = new Page<>(query.getPage(), query.getLimit());
        baseMapper.selectPage(page, wrapper);
        long total = page.getTotal();
        List<RoleVo> roles = page.getRecords().stream()
                .map(role -> {
                    RoleVo vo = new RoleVo();
                    BeanUtils.copyProperties(role, vo);
                    QueryWrapper<RolePermission> qw = new QueryWrapper<>();
                    qw.eq("role_id", vo.getId());
                    List<String> permissions = rolePermissionService.list(qw).stream()
                            .map(RolePermission::getPermissionId)
                            .collect(Collectors.toList());
                    vo.setPermissions(permissions);
                    return vo;
                }).collect(Collectors.toList());
        return new RoleListVo().setTotal(total).setRoles(roles);
    }

    @Override
    public List<RoleVo> fetchList() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("id", "role_name", "description");
        List<Role> roleList = baseMapper.selectList(wrapper);
        List<RoleVo> res = roleList.stream()
                .map(role -> {
                    RoleVo vo = new RoleVo();
                    BeanUtils.copyProperties(role, vo);
                    QueryWrapper<RolePermission> qw = new QueryWrapper<>();
                    qw.eq("role_id", vo.getId());
                    List<String> permissions = rolePermissionService.list(qw).stream()
                            .map(RolePermission::getPermissionId)
                            .collect(Collectors.toList());
                    vo.setPermissions(permissions);
                    return vo;
                }).collect(Collectors.toList());

        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(RoleParam roleParam) {
        Assert.notNull(roleParam, "roleParam is null");
        Assert.notNull(roleParam.getId(), "roleParam.id is null");
        Assert.notNull(roleParam.getRoleName(), "roleParam.id is null");

        /**
         * 先删除后更新
         */
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleParam.getId());
        rolePermissionService.remove(wrapper);

        if (roleParam.getPermissions() != null && roleParam.getPermissions().size() > 0) {
            List<RolePermission> rps = roleParam.getPermissions().stream().map(p -> {
                RolePermission rp = new RolePermission();
                rp.setRoleId(roleParam.getId());
                rp.setPermissionId(p);
                return rp;
            }).collect(Collectors.toList());
            rolePermissionService.saveBatch(rps);
        }

        Role role = roleParam.convertTo();
        baseMapper.updateById(role);
    }

    /**
     * todo
     *
     * @param roleParam
     */
    @Override
    public void create(RoleParam roleParam) {
        Role role = new Role();
        BeanUtils.copyProperties(roleParam, role);
        baseMapper.insert(role);
        String roleId = role.getId();
        roleParam.setId(roleId);

        List<RolePermission> rps = roleParam.getPermissions().stream().map(dto -> {
            RolePermission rp = new RolePermission();
            BeanUtils.copyProperties(dto, rp);
            rp.setRoleId(roleId);
            return rp;
        }).collect(Collectors.toList());

        role.setPermissions(null);

        rolePermissionService.saveBatch(rps);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        Assert.notNull(id, "roleId is null");
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("role_id", id);
            rolePermissionService.removeByMap(map);
            userRoleService.removeByMap(map);
            baseMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        idList.stream().forEach(this::removeById);
        return true;
    }

    @Override
    public Role getById(Serializable roleId) {

        Assert.notNull(roleId, "roleId is null");

        Role role = baseMapper.selectById(roleId);

        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        wrapper.select("permission_id");
        List<String> permissionIds = rolePermissionService.list(wrapper).stream().filter(Objects::nonNull)
                .map(RolePermission::getPermissionId).collect(Collectors.toList());

        List<Permission> permissions = new ArrayList<>();
        if (permissionIds.size() > 0) {
            permissions = permissionService.listByIds(permissionIds);
        }
        role.setPermissions(permissions);

        return role;
    }

    @Override
    public RoleDTO convertTo(Role source) {
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(source, roleDTO);
        List<String> permissionIds = source.getPermissions().stream().
                map(Permission::getId).collect(Collectors.toList());
        roleDTO.setPermissions(permissionIds);
        return roleDTO;
    }

    @Override
    public List<RoleDTO> convertTo(List<Role> sources) {
        throw new UnsupportedOperationException();
    }
}
