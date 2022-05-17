package com.api.sys.responseEntity;

import com.common.utils.page.BasePageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kcx
 * @description: 查询房源信息
 * @date 2022/5/10 13:22
 */
@Data
@ApiModel
public class ResponseUserAmountRecordListEntity{

    private String id;

    @ApiModelProperty(value = "房源名称")
    private String houseName;

    @ApiModelProperty(value = "开始出租日期")
    private String startDate;

    @ApiModelProperty(value = "结束出租日期")
    private String endDate;

    @ApiModelProperty(value = "客户名称")
    private String userName;

    @ApiModelProperty(value = "客户电话")
    private String userPhone;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "操作符")
    private String operator;

    @ApiModelProperty(value = "金额")
    private String amount;

}
