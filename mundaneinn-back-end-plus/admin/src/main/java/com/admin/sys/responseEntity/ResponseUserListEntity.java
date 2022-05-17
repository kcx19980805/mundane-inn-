package com.admin.sys.responseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账号管理 列表
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
public class ResponseUserListEntity {

    @ApiModelProperty(value = "序号")
    private int iid;

    @ApiModelProperty(value = "账号id")
    private String userId;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "所属管理组")
    private String permissionGroupName;

    @ApiModelProperty(value = "状态(0正常 1禁用)")
    private String status;
}
