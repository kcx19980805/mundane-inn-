package com.admin.sys.requestEntity;

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
public class RequestUserUpdatePasswordEntity {

    @ApiModelProperty(value = "id")
    private String userId;

    @NotBlank(message = "原密码不能为空")
    @ApiModelProperty(value = "原密码", required = true)
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

    @NotBlank(message = "确认新密码不能为空")
    @ApiModelProperty(value = "确认新密码", required = true)
    private String confirmPassword;

    @ApiModelProperty(value = "新密码的盐", hidden = true)
    private String salt;
}
