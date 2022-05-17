package com.api.sys.responseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kcx
 * @description: 创建支付宝订单
 * @date 2022/5/8 12:52
 */
@Data
@ApiModel
public class ResponseOrderAddEntity {

    @ApiModelProperty(value = "二维码")
    private String qrCode;

    @ApiModelProperty(value = "订单编号")
    private String out_trade_no;
}
