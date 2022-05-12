package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 平台 账号管理 修改
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
public class RequestUserUpdateEntity {

    @NotBlank(message = "账号id不能为空")
    @ApiModelProperty(value = "账号id", required = true)
    private String userId;

    @NotBlank(message = "所属管理组id不能为空")
    @ApiModelProperty(value = "所属管理组id", required = true)
    private String permissionGroupId;

}
