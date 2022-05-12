package com.api.sys.responseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author kcx
 * @description: 查询用户订单列表
 * @date 2022/4/30 21:50
 */
@Data
@ApiModel
public class ResponseUserOrderListEntity {
    @ApiModelProperty(value = "订单id")
    private String id;

    @ApiModelProperty(value = "房源id（client_house表id）")
    private String houseId;

    @ApiModelProperty(value = "房源名称")
    private String name;

    @ApiModelProperty(value = "详细位置（门牌号）")
    private String location;

    @ApiModelProperty(value = "用户id（client_user表id）")
    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始日期")
    private String startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "房源单日价格")
    private String housePrice;

    @ApiModelProperty(value = "房东折扣")
    private String discountLandlord;

    @ApiModelProperty(value = "房东折扣后的结算金额")
    private String amount;

    @ApiModelProperty(value = "系统折扣")
    private String discountSys;

    @ApiModelProperty(value = "实际支付金额")
    private String amountActual;

    @ApiModelProperty(value = "订单状态（0待支付1待入住2已完成3已取消4已退款）")
    private String state;

    @ApiModelProperty(value = "支付时间")
    private String payTime;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
