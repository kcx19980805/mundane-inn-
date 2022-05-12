package com.api.sys.requestEntity;

import com.common.utils.page.BasePageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kcx
 * @description: 用户余额变更记录列表
 * @date 2022/5/10 13:18
 */
@Data
@ApiModel
public class RequestUserAmountRecordListEntity  extends BasePageEntity {
    @ApiModelProperty(value = "用户id",hidden = true)
    private String userId;

    @ApiModelProperty(value = "房源名称")
    private String houseName;

    @ApiModelProperty(value = "操作符（+或-）")
    private String operator;

}
