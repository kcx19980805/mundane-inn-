package com.api.sys.responseEntity;

import com.api.sys.entity.SysNoticeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kcx
 * @description: 用户通知列表
 * @date 2022/4/26 21:38
 */
@Data
@ApiModel
public class ResponseNoticeListEntity {
    @ApiModelProperty("通知列表")
    private List<SysNoticeEntity> list;
    @ApiModelProperty("未读消息总数")
    private int unReadTotal;
}
