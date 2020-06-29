package com.ponking.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Peng
 * @date 2020/6/27--0:37
 **/
@Data
public class RoleVo {

    private String id;

    private String roleName;

    private String description;

    private List<String> permissions;

}
