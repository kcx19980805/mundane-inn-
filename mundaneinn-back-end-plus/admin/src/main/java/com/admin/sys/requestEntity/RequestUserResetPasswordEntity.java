package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 重置密码
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
public class RequestUserResetPasswordEntity {

    @NotBlank(message = "账号id不能为空")
    @ApiModelProperty(value = "账号id", required = true)
    private String userId;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

}
