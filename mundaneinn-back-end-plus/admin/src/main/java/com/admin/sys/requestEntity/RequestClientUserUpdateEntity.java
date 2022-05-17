package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户管理-修改
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
public class RequestClientUserUpdateEntity {

    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id",required = true)
    private String userId;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号",required = true)
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别(0男 1女)")
    private String sex;

    @ApiModelProperty("找回密码邮箱")
    private String email;
}
