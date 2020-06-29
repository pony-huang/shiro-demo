package com.ponking.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.model.entity.User;
import com.ponking.model.entity.UserRole;
import com.ponking.model.params.LoginParam;
import com.ponking.model.result.Result;
import com.ponking.service.PermissionService;
import com.ponking.service.UserService;
import com.ponking.utils.JwtUtil;
import com.ponking.utils.LoginUtil;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @date 2020/6/25--14:34
 **/
@RestController
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @PostMapping("login")
    @ApiModelProperty("登录")
    public Result login(@RequestBody LoginParam loginParam) {
        Assert.notNull(loginParam, "loginParam is null");
        LoginUtil.login(loginParam);
        String token = JwtUtil.createToken(userService.getOne(
                new QueryWrapper<User>().eq("username", loginParam.getUsername())));
        HashMap<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success().data(data);
    }

    @GetMapping("info")
    @ApiModelProperty("获取权限和用户信息")
    public Result login(@RequestParam("token") String token) {
        Assert.notNull(token, "This token is empty");
        return Result.success().data(getPermissions(token));

    }

    @PostMapping("logout")
    @ApiModelProperty("注销")
    public Result logout() {
        LoginUtil.logout();
        return Result.success();
    }

    /**
     * 模拟数据测试
     *
     * @param token
     * @return
     */
    private Map<String, Object> getPermissions(String token) {
        HashMap<String, Object> res = new HashMap<>();


        String username = JwtUtil.getUsername(token);

        List<String> roles = permissionService.listPermissionByUserName(username);
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));

        // 暂时模拟数据 todo
        roles.addAll(Arrays.asList("admin", "editor"));

        res.put("roles", roles);
        res.put("introduction", "I am a " + user.getNickName());
        res.put("avatar", user.getAvatar());
        res.put("name", user.getNickName());
        return res;
    }
}
