package com.admin.sys.requestEntity;

import com.common.utils.page.BasePageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理-列表
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
public class RequestClientUserListEntity extends BasePageEntity {

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "手机号")
    private String phone;
}
