package com.ponking.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Peng
 * @date 2020/7/11--10:38
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("acl_log")
@ApiModel(value="日志对象", description="日志")
public class Log {

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;


    @ApiModelProperty(value = "该操作的用户账号")
    private String username;

    @ApiModelProperty(value = "操作")
    private String operation;

    @ApiModelProperty(value = "用户登录地址")
    private String host;

    @ApiModelProperty(value = "操作uri")
    private String uri;

    @ApiModelProperty(value = "用户设备(系统+浏览器)",notes = "Windows;Chrome")
    private String device;

    @ApiModelProperty(value = "用户编号")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
