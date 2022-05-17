package com.api.sys.responseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kcx
 * @description: 房源评论列表
 * @date 2022/5/3 13:53
 */
@Data
@ApiModel
public class ResponseHouseCommentListEntity {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户id")
    private String headImg;

    @ApiModelProperty(value = "用户电话")
    private String phone;

    @ApiModelProperty(value = "房东id")
    private String landlordId;

    @ApiModelProperty(value = "房源id")
    private String houseId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论星级")
    private String score;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
