package com.api.sys.responseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kcx
 * @description: 订单列表
 * @date 2022/5/7 16:50
 */
@Data
@ApiModel
public class ResponseOrderListEntity {

    @ApiModelProperty(value = "订单id")
    private String id;

    @ApiModelProperty(value = "房源id")
    private String houseId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始日期")
    private String startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "房源名称")
    private String name;

    @ApiModelProperty(value = "详细位置（门牌号）")
    private String location;

    @ApiModelProperty(value = "客户姓名")
    private String userName;

    @ApiModelProperty(value = "客户电话")
    private String userPhone;

    @ApiModelProperty(value = "房东姓名")
    private String landlordName;

    @ApiModelProperty(value = "房东电话")
    private String landlordPhone;

    @ApiModelProperty(value = "订单状态（0待支付1待入住2已完成3已取消4已退款）")
    private String state;

    @ApiModelProperty(value = "实际支付金额")
    private String amountActual;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "剩余支付时间，前端算")
    private String timeLeft;
}
