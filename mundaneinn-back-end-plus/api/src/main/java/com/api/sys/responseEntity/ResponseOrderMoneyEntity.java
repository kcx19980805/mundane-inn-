package com.api.sys.responseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kcx
 * @description: 查询用户订单入账信息
 * @date 2022/5/10 12:49
 */
@Data
@ApiModel
public class ResponseOrderMoneyEntity {

    @ApiModelProperty(value = "已入账")
    private String beenCredited;

    @ApiModelProperty(value = "待入账")
    private String pending;

}
