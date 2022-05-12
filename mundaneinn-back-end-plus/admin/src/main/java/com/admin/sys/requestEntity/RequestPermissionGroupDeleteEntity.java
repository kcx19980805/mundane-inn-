package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 管理组 删除
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
public class RequestPermissionGroupDeleteEntity {

    @NotBlank(message = "管理组id不能为空")
    @ApiModelProperty(value = "管理组id(多个逗号隔开)", required = true)
    private String permissionGroupId;


}
