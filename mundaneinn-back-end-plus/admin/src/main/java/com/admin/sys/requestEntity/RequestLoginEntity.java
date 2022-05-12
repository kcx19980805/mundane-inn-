package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
public class RequestLoginEntity {

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号", required = true)
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
