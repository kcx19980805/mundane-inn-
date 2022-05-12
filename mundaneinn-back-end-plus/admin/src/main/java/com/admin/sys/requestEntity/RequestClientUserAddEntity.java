package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户管理-新增
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
public class RequestClientUserAddEntity {

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    @Pattern(regexp = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)",message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty(value = "性别(0男 1女)")
    @Pattern(regexp = "^[0-1]$",message = "性别格式不正确")
    private String sex;

    @NotBlank(message = "找回密码邮箱不能为空")
    @ApiModelProperty("找回密码邮箱")
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message = "邮箱格式不正确")
    private String email;
}
