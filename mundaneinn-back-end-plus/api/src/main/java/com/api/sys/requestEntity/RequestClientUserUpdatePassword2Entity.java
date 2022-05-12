package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 找回密码-邮箱验证通过-修改密码
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
@ApiModel
public class RequestClientUserUpdatePassword2Entity {
    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码,base64加密", required = true)
    private String newPassword;

    @NotBlank(message = "确认新密码不能为空")
    @ApiModelProperty(value = "确认新密码,base64加密", required = true)
    private String confirmPassword;

    @ApiModelProperty(value = "新密码的盐", hidden = true)
    private String salt;
}
