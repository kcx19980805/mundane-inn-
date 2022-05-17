package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kcx
 * @description: 查询城市
 * @date 2022/4/27 16:54
 */
@Data
@ApiModel
public class RequestCityListEntity {

    @ApiModelProperty(value = "是否按拼音排序,0否1是")
    private String orderByPingYin;

}
