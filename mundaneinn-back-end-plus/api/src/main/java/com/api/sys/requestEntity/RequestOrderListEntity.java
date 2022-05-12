package com.api.sys.requestEntity;

import com.common.utils.page.BasePageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author kcx
 * @description: 订单列表
 * @date 2022/5/7 16:32
 */
@Data
@ApiModel
public class RequestOrderListEntity extends BasePageEntity {
    @ApiModelProperty(value = "用户id",hidden = true)
    private String userId;

    @ApiModelProperty(value = "房源名称")
    private String houseName;

    @ApiModelProperty(value = "城市编码")
    private String treeCode;

    @ApiModelProperty(value = "入住状态（0待支付1待入住2已完成3已取消4已退款）")
    private String state;
}
