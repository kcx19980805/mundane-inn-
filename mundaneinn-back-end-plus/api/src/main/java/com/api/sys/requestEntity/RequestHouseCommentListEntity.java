package com.api.sys.requestEntity;

import com.common.utils.page.BasePageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kcx
 * @description: 房源评论列表
 * @date 2022/5/3 13:53
 */
@Data
@ApiModel
public class RequestHouseCommentListEntity extends BasePageEntity {

    @NotBlank(message = "房源id不能为空")
    @ApiModelProperty(value = "房源id",required = true)
    private String houseId;

    @ApiModelProperty(value = "房东id")
    private String landlordId;

}
