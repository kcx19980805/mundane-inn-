package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 *  账号管理-新增
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
public class RequestUserAddEntity {

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号", required = true)
    private String account;

    @NotBlank(message = "所属管理组id不能为空")
    @ApiModelProperty(value = "所属管理组id", required = true)
    private String permissionGroupId;

}
