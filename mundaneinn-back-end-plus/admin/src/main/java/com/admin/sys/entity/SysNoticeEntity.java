package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 系统通知表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("sys_notice")
public class SysNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 发送类型（0程序自动发送1管理员发送）
	 */
	private String sendType;
	/**
	 * 接收用户id（client_user表id）
	 */
	private Long userId;
	/**
	 * 通知类型（0系统通知1优惠促销）
	 */
	private String noticeType;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 链接地址
	 */
	private String linkUrl;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 是否已读（0否1是）
	 */
	private String isRead;
	/**
	 * 是否删除(0未删除 1已删除）
	 */
	private String isDel;

}
