package com.admin.sys.requestEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户管理-删除
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
public class RequestClientUserDeleteEntity {
    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private String userId;
}
