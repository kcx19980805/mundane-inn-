package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kcx
 * @description: 删除/取消/支付订单
 * @date 2022/5/6 21:51
 */
@Data
@ApiModel
public class RequestOrderInfoEntity {

    @NotBlank(message = "订单id不能为空")
    @ApiModelProperty(value = "订单id,必填", hidden = true)
    private String orderId;

}
