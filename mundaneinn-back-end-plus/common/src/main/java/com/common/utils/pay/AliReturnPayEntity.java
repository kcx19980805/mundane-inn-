package com.common.utils.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 支付宝回调参数
 */
@Data
public class AliReturnPayEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 开发者的app_id
     */
    private String app_id;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 支付宝交易流水号
     */
    private String trade_no;

    /**
     * 签名
     */
    private String sign;

    /**
     * 交易状态
     */
    private String trade_status;

    /**
     * 交易的金额
     */
    private String total_amount;

    /**
     * 交易参数
     */
    private String body;
}

