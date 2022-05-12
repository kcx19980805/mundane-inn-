package com.api.sys.requestEntity;

import com.common.utils.regular.RegUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author kcx
 * @description: 发送手机验证码
 * @date 2022/4/26 15:35
 */
@Data
@ApiModel
public class RequestSendSmsEntity {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegUtils.Phone,message = "手机号格式错误")
    @ApiModelProperty(value = "手机号",required = true)
    private  String phone;
}
