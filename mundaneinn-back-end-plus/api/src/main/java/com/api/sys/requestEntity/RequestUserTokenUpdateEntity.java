package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户token 更新
 */
@Data
@ApiModel
public class RequestUserTokenUpdateEntity {

    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @NotBlank(message = "用户token不能为空")
    @ApiModelProperty(value = "token", required = true)
    private String token;
}
