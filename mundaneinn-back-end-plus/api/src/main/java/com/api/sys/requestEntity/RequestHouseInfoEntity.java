package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kcx
 * @description: 查询房源信息
 * @date 2022/4/27 16:54
 */
@Data
@ApiModel
public class RequestHouseInfoEntity {
    @NotBlank(message = "房源id不能为空")
    @ApiModelProperty(value = "房源id",required = true)
    private String houseId;
}
