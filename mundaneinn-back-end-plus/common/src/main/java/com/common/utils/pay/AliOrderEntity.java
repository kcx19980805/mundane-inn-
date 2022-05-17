package com.common.utils.pay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 支付宝商品订单实体类(支付实体对象)
 */
@Data
@ApiModel
public class AliOrderEntity {

    @ApiModelProperty(value = "商户订单号,必填", hidden = true)
    private String out_trade_no;

    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id，业务上必须", required = true)
    private String userId;

    @NotBlank(message = "订单名称不能为空")
    @ApiModelProperty(value = "订单名称,必填", required = true)
    private String subject;

    @NotBlank(message = "付款金额不能为空")
    @ApiModelProperty(value = "付款金额,必填,根据支付宝接口协议，必须使用下划线", required = true)
    private String total_amount;

    @ApiModelProperty(value = "商品描述，可空")
    private String description;

    @ApiModelProperty(value = "超时时间参数",hidden = true)
    private String timeout_express = "10m";

    @ApiModelProperty(value = "产品编号",hidden = true)
    private String product_code = "FAST_INSTANT_TRADE_PAY";
}
