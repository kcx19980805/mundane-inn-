package com.admin.sys.responseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理-列表
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
public class ResponseClientUserListEntity {

    @ApiModelProperty(value = "序号")
    private int iid;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "状态(0启用 1禁用)")
    private String status;

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

    @ApiModelProperty(value = "房东评分")
    private String score;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "余额")
    private String balance;
}
