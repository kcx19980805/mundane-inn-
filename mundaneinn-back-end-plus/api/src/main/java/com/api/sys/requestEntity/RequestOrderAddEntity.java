package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
/**
 * @author kcx
 * @description: 创建订单
 * @date 2022/5/6 18:53
 */
@Data
@ApiModel
public class RequestOrderAddEntity {

    @NotBlank(message = "房源id不能为空")
    @ApiModelProperty(value = "房源id",required = true)
    private String houseId;

    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id",required = true)
    private String userId;

    @NotBlank(message = "开始日期不能为空")
    @ApiModelProperty(value = "开始日期",required = true)
    private String startDate;

    @NotBlank(message = "结束日期不能为空")
    @ApiModelProperty(value = "结束日期",required = true)
    private String endDate;

    @NotBlank(message = "付款金额不能为空")
    @ApiModelProperty(value = "付款金额,必填,根据支付宝接口协议，必须使用下划线", required = true)
    private String total_amount;
}
