package com.api.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户聊天记录表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("client_user_chat")
public class ClientUserChatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 消息发送用户id（client_user表id）
	 */
	private Long sendUserId;
	/**
	 * 消息接收用户id（client_user表id）
	 */
	private Long acceptUserId;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 是否已读（0否1是）
	 */
	private String isRead;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 发送用户是否删除此消息(0未删除 1已删除）
	 */
	private String isDelSend;
	/**
	 * 接收用户是否删除此消息(0未删除 1已删除）
	 */
	private String isDelAccept;

}
