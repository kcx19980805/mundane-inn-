package com.api.sys.requestEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kcx
 * @description: 新增/修改房源
 * @date 2022/5/9 14:22
 */
@Data
@ApiModel
public class RequestHouseUpdateEntity {

    @ApiModelProperty(value = "房源id")
    private String id;

    @ApiModelProperty(value = "房源图片")
    private List<String> images;

    @ApiModelProperty(value = "城市名称")
    private String city;

    @ApiModelProperty(value = "房源名称")
    private String name;

    @ApiModelProperty(value = "详细位置（门牌号）")
    private String location;

    @ApiModelProperty(value = "房型（0小区住宅1公寓）")
    private String residenceType;

    @ApiModelProperty(value = "客厅数量")
    private String parlorNumber;

    @ApiModelProperty(value = "卫生间数量")
    private String bathroomNumber;

    @ApiModelProperty(value = "厨房数量")
    private String kitchenNumber;

    @ApiModelProperty(value = "人数1-10,10表示10人及以上")
    private String peopleNumber;

    @ApiModelProperty(value = "床数1-10,10表示10人及以上")
    private String bedNumber;

    @ApiModelProperty(value = "户型1-4,4表示4户及以上")
    private String houseType;

    @ApiModelProperty(value = "房源描述")
    private String describe;

    @ApiModelProperty(value = "配套id")
    private List<String> matchList;

    @ApiModelProperty(value = "房型id")
    private List<String> residenceList;

    @ApiModelProperty(value = "经度")
    private String pointLng;

    @ApiModelProperty(value = "纬度")
    private String pointLat;

    @ApiModelProperty(value = "房源价格")
    private String housePrice;

    @ApiModelProperty(value = "折扣")
    private String discount;

    @ApiModelProperty(value = "开始出租日期")
    private String startDate;

    @ApiModelProperty(value = "结束出租日期")
    private String endDate;

    @ApiModelProperty(value = "所属城市编码")
    private String treeCode;

    @ApiModelProperty(value = "出租类型（0单间1整套）")
    private String rentalType;

    @ApiModelProperty(value = "是否近地铁0是1否")
    private String isNearSubway;

    @ApiModelProperty(value = "房东id",hidden = true)
    private String userId;

    @ApiModelProperty(value = "坐标点",hidden = true)
    private String point;
}
