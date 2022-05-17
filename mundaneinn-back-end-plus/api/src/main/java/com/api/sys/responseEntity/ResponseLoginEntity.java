package com.api.sys.responseEntity;

import com.common.constant.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录
 */
@Data
@ApiModel
public class ResponseLoginEntity {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty(Constants.TOKEN+"(请求体header中参数名用 "+Constants.TOKEN+" )")
    private String token;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String headImg;
}
