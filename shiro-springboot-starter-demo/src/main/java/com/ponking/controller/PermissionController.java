package com.ponking.controller;


import com.ponking.aop.annotation.Log;
import com.ponking.model.entity.Permission;
import com.ponking.model.result.Result;
import com.ponking.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限(菜单) 前端控制器
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
@RestController
@RequestMapping("/api/admin/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @GetMapping
    @ApiOperation("获取菜单列表")
    @RequiresPermissions("permission:list")
    public Result fetchList(){
        return Result.success();
    }


    @GetMapping("{id}")
    @ApiOperation("获取菜单信息")
    @RequiresPermissions("permission:list")
    public Result getById(@PathVariable("id")String id){
        Assert.notNull(id,"id is null");
        return Result.success();
    }

    @Log
    @PostMapping
    @ApiOperation("添加菜单")
    @RequiresPermissions("permission:add")
    public Result createBy(@RequestBody Permission permission){
        Assert.notNull(permission,"permissionParam is null");
        return Result.success();
    }

    @Log
    @ApiOperation("更新菜单")
    @PutMapping("{id}")
    @RequiresPermissions("permission:update")
    public Result updateBy(@PathVariable("id")String id,@RequestBody Permission permissionParam){
        Assert.notNull(id,"id is null");
        return Result.success();
    }


    @Log
    @ApiOperation("删除菜单")
    @DeleteMapping("{id}")
    @RequiresPermissions("permission:remove")
    public Result deleteById(@PathVariable("id")String id){
        permissionService.removeById(id);
        return Result.success();
    }


    @Log
    @ApiOperation("删除菜单")
    @DeleteMapping
    @RequiresPermissions("permission:remove")
    public Result deleteByIds(@RequestBody List<String> ids){
        permissionService.removeByIds(ids);
        return Result.success();
    }
}
