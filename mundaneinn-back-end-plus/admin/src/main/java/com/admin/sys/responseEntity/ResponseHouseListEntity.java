package com.admin.sys.responseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kcx
 * @description: 查询房源列表
 * @date 2022/4/30 21:50
 */
@Data
@ApiModel
public class ResponseHouseListEntity {

    @ApiModelProperty(value = "序号")
    private int iid;

    @ApiModelProperty(value = "房源id")
    private String id;

    @ApiModelProperty(value = "房源名称")
    private String name;

    @ApiModelProperty(value = "房源图片")
    private List<String> images;

    @ApiModelProperty(value = "房源价格")
    private String housePrice;

    @ApiModelProperty(value = "折扣")
    private String discount;

    @ApiModelProperty(value = "出租类型（0单间1整套）")
    private String rentalType;

    @ApiModelProperty(value = "人数1-10,10表示10人及以上")
    private String peopleNumber;

    @ApiModelProperty(value = "床数1-10,10表示10人及以上")
    private String bedNumber;

    @ApiModelProperty(value = "户型1-4,4表示4户及以上")
    private String houseType;

    @ApiModelProperty(value = "是否近地铁0是1否")
    private String isNearSubway;

    @ApiModelProperty(value = "综合评分")
    private String score;

    @ApiModelProperty(value = "评论数量")
    private String commentsNumber;

    @ApiModelProperty(value = "房东头像")
    private String headImg;

    @ApiModelProperty(value = "房源状态0=待审核1=已上架2=审核失败3=已下架")
    private String status;

    @ApiModelProperty(value = "距离")
    private String distance;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始出租日期")
    private String startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束出租日期")
    private String endDate;

    @ApiModelProperty(value = "房源位置")
    private String location;

}
