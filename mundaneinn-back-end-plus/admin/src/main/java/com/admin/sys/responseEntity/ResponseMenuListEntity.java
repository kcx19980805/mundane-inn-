package com.admin.sys.responseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 后台系统 菜单列表
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
@Data
public class ResponseMenuListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单id")
    private String id;

    @ApiModelProperty("父菜单ID，一级菜单为0")
    private String parentId;

    @ApiModelProperty("目录/菜单名称")
    private String name;

    @ApiModelProperty("菜单URL")
    private String url;

    @ApiModelProperty("类型(0目录 1菜单 2按钮)")
    private Integer type;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("排序")
    private Integer orderNum;

    @ApiModelProperty("二级菜单")
    private List<?> list;

}
