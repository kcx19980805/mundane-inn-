package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kcx
 * @description: 用户管理-单个信息
 * @date 2022/5/4 19:08
 */
@Data
@ApiModel
public class RequestClientUserInfoEntity {
    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id",required = true)
    private String userId;
}
