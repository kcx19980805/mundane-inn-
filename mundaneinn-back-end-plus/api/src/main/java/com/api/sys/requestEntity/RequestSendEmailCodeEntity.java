package com.api.sys.requestEntity;

import com.common.utils.regular.RegUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 登录-发送邮箱验证码
 */
@Data
@ApiModel
public class RequestSendEmailCodeEntity {
    @NotBlank(message = "邮箱地址不能为空")
    @Pattern(regexp = RegUtils.Email,message = "邮箱格式错误")
    @ApiModelProperty(value = "邮箱地址",required = true)
    private  String email;
}
