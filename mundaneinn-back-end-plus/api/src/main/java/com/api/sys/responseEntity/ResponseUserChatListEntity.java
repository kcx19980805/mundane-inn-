package com.api.sys.responseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kcx
 * @description: 用户聊天记录列表
 * @date 2022/4/30 21:50
 */
@Data
@ApiModel
public class ResponseUserChatListEntity {

    @ApiModelProperty(value = "消息发送用户id")
    private Long sendUserId;

    @ApiModelProperty(value = "消息接收用户id")
    private Long acceptUserId;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "是否已读（0否1是）")
    private String isRead;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "发送用户是否删除此消息(0未删除 1已删除）")
    private String isDelSend;

    @ApiModelProperty(value = "接收用户是否删除此消息(0未删除 1已删除）")
    private String isDelAccept;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户电话")
    private String phone;

    @ApiModelProperty(value = "用户头像")
    private String headImg;

    @ApiModelProperty(value = "是否在线（0否 1是）")
    private String isOnline;

}
