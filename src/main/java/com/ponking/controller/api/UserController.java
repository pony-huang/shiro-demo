package com.ponking.controller.api;

import com.ponking.model.params.UserParam;
import com.ponking.model.params.UserQuery;
import com.ponking.model.result.Result;
import com.ponking.model.vo.UserListVo;
import com.ponking.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Peng
 * @date 2020/6/26--17:59
 **/
@RequestMapping("/api/admin/users")
@ApiModel("用户管理")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation("获取用户分页")
    @RequiresPermissions("user:list")
    public Result fetchList(UserQuery query){
        UserListVo data = userService.fetchList(query);
        return Result.success(data);
    }

    @GetMapping("{id:\\d+}")
    @RequiresPermissions("user:list")
    @ApiOperation("获取用户")
    public Result getById(@PathVariable("id")String id){
        return Result.success().data(userService.convertTo(userService.getById(id)));
    }

    @PostMapping
    @ApiOperation("添加用户")
    @RequiresPermissions("user:add")
    public Result createBy(@RequestBody UserParam userParam){
        userService.create(userParam);
        return Result.success();
    }

    @PutMapping("{id}")
    @ApiOperation("分配角色")
    @RequiresPermissions("user:assign")
    public Result assignBy(@PathVariable("id")String userId,@RequestBody List<String> roles){
        userService.assignRoles(userId,roles);
        return Result.success();
    }

    @DeleteMapping("{id:\\d+}")
    @RequiresPermissions("user:remove")
    @ApiOperation("删除用户")
    public Result deleteById(@PathVariable("id")String id){
        userService.removeById(id);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("批量删除用户")
    @RequiresPermissions("user:remove")
    public Result deleteByIds(@RequestBody List<String> ids){
        userService.removeByIds(ids);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("更新用户信息")
    @RequiresPermissions("user:update")
    public Result updateById(@RequestBody UserParam userParam){
        Assert.notNull(userParam,"userParam is null");
        userService.updateById(userParam);
        return Result.success();
    }
}
