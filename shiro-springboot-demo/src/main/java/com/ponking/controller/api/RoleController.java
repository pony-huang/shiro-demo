package com.ponking.controller.api;


import com.ponking.aop.annotation.Log;
import com.ponking.model.params.RoleParam;
import com.ponking.model.result.Result;
import com.ponking.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
@RestController
@RequestMapping("/api/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

//    @GetMapping
//    @ApiOperation("获取角色列表")
////    @RequiresPermissions("role:list")
//    public Result fetchList(RoleQuery query){
//        RoleListVo data = roleService.fetchList(query);
//        return Result.success(data);
//    }

    @GetMapping
    @ApiOperation("获取角色列表")
    @RequiresPermissions("role:list")
    public Result fetchList(){
        return Result.success(roleService.fetchList());
    }

    @GetMapping("{id:\\d+}")
    @ApiOperation("获取角色")
    @RequiresPermissions("role:list")
    public Result getById(@PathVariable("id")String id){
        return Result.success().data(roleService.convertTo(roleService.getById(id)));
    }

    @Log
    @PostMapping
    @ApiOperation("添加角色")
    @RequiresPermissions("role:add")
    public Result createBy(@RequestBody RoleParam roleParam){
        roleService.create(roleParam);
        return Result.success().data(roleParam);
    }

    @Log
    @PutMapping("{id}")
    @ApiOperation("更新角色")
    @RequiresPermissions("role:update")
    public Result updateBy(@PathVariable("id")String id,@RequestBody RoleParam roleParam){
        Assert.notNull(id,"id is null");
        roleService.updateById(roleParam);
        return Result.success();
    }

    @Log
    @DeleteMapping("{id:\\d+}")
    @ApiOperation("删除角色")
    @RequiresPermissions("role:remove")
    public Result deleteById(@PathVariable("id")String id){
        Assert.notNull(id,"id is null");
        roleService.removeById(id);
        return Result.success();
    }

    @Log
    @DeleteMapping
    @ApiOperation("批量删除角色")
    @RequiresPermissions("role:remove")
    public Result deleteByIds(@RequestBody List<String> ids){
        roleService.removeByIds(ids);
        return Result.success();
    }


}
