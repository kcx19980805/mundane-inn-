package com.api.sys.requestEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kcx
 * @description: 删除余额变更记录表
 * @date 2022/5/10 13:31
 */
@Data
@ApiModel
public class RequestUserAmountRecordDeleteEntity {
    @NotBlank(message = "记录id不能为空")
    @ApiModelProperty(value = "记录id")
    private String id;

}
