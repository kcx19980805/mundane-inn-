package com.admin.sys.responseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账号管理 单个信息
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
public class ResponseUserInfoEntity {

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "所属管理组")
    private String permissionGroupName;

    @ApiModelProperty(value = "所属管理组id")
    private String permissionGroupId;
}
