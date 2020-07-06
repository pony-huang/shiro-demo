package com.ponking.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.constant.AuthRealmConstants;
import com.ponking.exception.GlobalException;
import com.ponking.model.entity.Permission;
import com.ponking.model.entity.User;
import com.ponking.service.PermissionService;
import com.ponking.service.RoleService;
import com.ponking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Peng
 * @date 2020/6/25--16:00
 **/
@Slf4j
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("===>>> 权限配置中...");
        String username = principalCollection.getPrimaryPrincipal().toString();
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new GlobalException("no user");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        roleService.listRolesByUserName(user.getUsername())
                .stream().forEach(info::addRole);
        permissionService.listPermissionByUserName(user.getUsername())
                .stream().forEach(info::addStringPermission);
        log.info("===>>> 完成权限配置");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("===>>> 正在验证身份...");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if(user == null){
            throw new UnknownAccountException();
        }
        if(user.getState() != AuthRealmConstants.LOCKED_ACCOUNT){
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(username,user.getPassword(),
                        ByteSource.Util.bytes(user.getSalt()),getName());
        log.info("===>>> 验证身份成功");
        return info;
    }
}
