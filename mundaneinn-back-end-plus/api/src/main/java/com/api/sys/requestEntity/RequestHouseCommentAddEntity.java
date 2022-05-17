package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kcx
 * @description: 新增房源评论
 * @date 2022/5/8 18:23
 */
@Data
@ApiModel
public class RequestHouseCommentAddEntity {
    @NotBlank(message = "订单id不能为空")
    @ApiModelProperty(value = "订单id",required = true)
    private String orderId;

    @NotBlank(message = "房源id不能为空")
    @ApiModelProperty(value = "房源id",required = true)
    private String houseId;

    @NotBlank(message = "评论内容不能为空")
    @ApiModelProperty(value = "评论内容",required = true)
    private String content;

    @NotBlank(message = "评分不能为空")
    @ApiModelProperty(value = "评分",required = true)
    private String score;
}
