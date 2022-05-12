package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改密码
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
@ApiModel
public class RequestClientUserUpdatePassword1Entity {
    @ApiModelProperty(value = "原密码,base64加密")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码,base64加密", required = true)
    private String newPassword;

    @NotBlank(message = "确认新密码不能为空")
    @ApiModelProperty(value = "确认新密码,base64加密", required = true)
    private String confirmPassword;
}
