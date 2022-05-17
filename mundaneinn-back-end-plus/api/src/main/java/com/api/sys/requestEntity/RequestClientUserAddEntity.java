package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户管理-新增
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
@ApiModel
public class RequestClientUserAddEntity {

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号,base64加密",required = true)
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码,base64加密",required = true)
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱(用于找回密码)",required = true)
    private String email;
}
