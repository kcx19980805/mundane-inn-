package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 账号管理-删除/禁用/启用
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Data
public class RequestUserDeleteEntity {

    @NotBlank(message = "账号id不能为空")
    @ApiModelProperty(value = "账号id(多个逗号隔开)", required = true)
    private String userId;

}
