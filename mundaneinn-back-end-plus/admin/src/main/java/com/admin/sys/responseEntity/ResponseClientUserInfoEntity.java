package com.admin.sys.responseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理-单个信息
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
public class ResponseClientUserInfoEntity {
    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别(0男 1女)")
    private String sex;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
