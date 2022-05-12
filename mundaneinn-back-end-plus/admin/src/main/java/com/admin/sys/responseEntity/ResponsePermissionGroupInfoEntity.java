package com.admin.sys.responseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 管理组 单个 信息
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
public class ResponsePermissionGroupInfoEntity {


    @ApiModelProperty(value = "管理组id")
    private String permissionGroupId;

    @ApiModelProperty(value = "组名")
    private String name;

    @ApiModelProperty(value = "管理组关联的菜单id数组")
    private List<String> menuIds;


}
