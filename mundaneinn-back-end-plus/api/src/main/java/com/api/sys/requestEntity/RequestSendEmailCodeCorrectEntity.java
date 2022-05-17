package com.api.sys.requestEntity;

import com.common.utils.regular.RegUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 登录-找回密码-验证码是否正确
 * @author Administrator
 */
@Data
@ApiModel
public class RequestSendEmailCodeCorrectEntity {

    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = RegUtils.Email,message = "邮箱格式错误")
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @NotBlank(message = "邮箱验证码不能为空")
    @ApiModelProperty(value = "邮箱验证码", required = true)
    private String emailCode;
}
