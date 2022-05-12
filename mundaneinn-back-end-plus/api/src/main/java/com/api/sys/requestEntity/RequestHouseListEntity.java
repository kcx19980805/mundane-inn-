package com.api.sys.requestEntity;

import com.common.utils.page.BasePageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author kcx
 * @description: 查询房源列表
 * @date 2022/4/27 16:54
 */
@Data
@ApiModel
public class RequestHouseListEntity extends BasePageEntity {

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "城市编码")
    private String cityCode;

    @ApiModelProperty(value = "地区名称")
    private String region;

    @ApiModelProperty(value = "经度")
    private String pointLng;

    @ApiModelProperty(value = "纬度")
    private String pointLat;

    @ApiModelProperty(value = "开始时间 yyyy-MM-dd")
    private String startTime;

    @ApiModelProperty(value = "结束时间 yyyy-MM-dd")
    private String endTime;

    @ApiModelProperty(value = "人数1-10,10表示10人及以上")
    private String peopleNumber;

    @ApiModelProperty(value = "床数1-10,10表示10床及以上")
    private String bedNumber;

    @ApiModelProperty(value = "户型1-4,4表示4户及以上")
    private String houseType;

    @ApiModelProperty(value = "房价")
    private String housePrice;

    @ApiModelProperty(value = "出租类型（0单间1整套）")
    private String rentalType;

    @ApiModelProperty(value = "配套id")
    private List<String> matchList;

    @ApiModelProperty(value = "配套条件数量",hidden = true)
    private Integer matchListTotal;

    @ApiModelProperty(value = "房型id")
    private List<String> residenceList;

    @ApiModelProperty(value = "房型条件数量",hidden = true)
    private Integer residenceListTotal;

    @ApiModelProperty(value = "是否推荐房源（0不推荐1推荐）")
    private String isRecommend;

    @ApiModelProperty(value = "推荐房源id",hidden = true)
    private List<Long> recommendHouseIds;

    @ApiModelProperty(value = "搜索范围（单位/公里）",hidden = true)
    private Integer distance;

    @ApiModelProperty(value = "按价格升序排序（0否1是）")
    private String orderByPriceAsc;

    @ApiModelProperty(value = "按价格降序排序（0否1是）")
    private String orderByPriceDesc;

    @ApiModelProperty(value = "按好评排序（0否1是）")
    private String orderByScore;

    @ApiModelProperty(value = "房源名称")
    private String houseName;

    @ApiModelProperty(value = "房源状态0=待审核1=已上架2=审核失败3=已下架")
    private String state;

    @ApiModelProperty(value = "房东id",hidden = true)
    private String userId;
}
