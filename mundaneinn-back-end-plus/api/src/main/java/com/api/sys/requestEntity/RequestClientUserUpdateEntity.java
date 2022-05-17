package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 用户管理-修改
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
@ApiModel
public class RequestClientUserUpdateEntity {

    @ApiModelProperty(value = "用户id",hidden = true)
    private String userId;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别(0男 1女)")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @NotBlank(message = "身份证号不能为空")
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "城市码（sys_area表tree_code）")
    private String cityCode;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String phone;

}
