package com.api.sys.responseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author kcx
 * @description: 用户常用地区-列表
 * @date 2022/4/28 21:25
 */
@Data
@ApiModel
public class ResponseAreaPopularListEntity {
    @ApiModelProperty(value = "位置名称")
    private String name;
    @ApiModelProperty(value = "经度坐标")
    private String pointLng;
    @ApiModelProperty(value = "纬度坐标")
    private String pointLat;
}
