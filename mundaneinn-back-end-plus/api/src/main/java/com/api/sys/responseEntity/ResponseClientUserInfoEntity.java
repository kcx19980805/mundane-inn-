package com.api.sys.responseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户管理-单个信息
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
@ApiModel
public class ResponseClientUserInfoEntity {
    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别(0男 1女)")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "城市码（sys_area表tree_code）")
    private String cityCode;

    @ApiModelProperty(value = "房东评分")
    private BigDecimal score;

    @ApiModelProperty(value = "是否在线（0否 1是）")
    private String isOnline;

    @ApiModelProperty(value = "是否实名认证（0否 1是）")
    private String isCertified;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "找回密码邮箱")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
