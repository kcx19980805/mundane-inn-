package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kcx
 * @description: 新增/修改房源
 * @date 2022/5/9 14:22
 */
@Data
@ApiModel
public class RequestHouseUpdateEntity {

    @ApiModelProperty(value = "房源id")
    private String id;

    @ApiModelProperty(value = "状态0=待审核1=已上架2=审核失败3=已下架")
    private String state;

    @ApiModelProperty(value = "审核失败原因")
    private String reason;

}
