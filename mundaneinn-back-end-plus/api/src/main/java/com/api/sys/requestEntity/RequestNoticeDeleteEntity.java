package com.api.sys.requestEntity;

import com.api.sys.entity.SysNoticeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author kcx
 * @description:批量删除/已读通知
 * @date 2022/4/26 21:45
 */
@Data
@ApiModel
public class RequestNoticeDeleteEntity {
    @ApiModelProperty(value = "通知id", required = true)
    private List<String> noticeIds;
}
