package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kcx
 * @description:
 * @date 2022/5/1 22:41
 */
@Data
@ApiModel
public class RequestCollectUpdateEntity {
    @NotBlank(message = "房源id不能为空")
    @ApiModelProperty(value = "房源id", required = true)
    private String houseId;
}
