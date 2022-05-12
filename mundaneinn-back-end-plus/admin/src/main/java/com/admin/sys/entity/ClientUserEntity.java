package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户管理
 *
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Data
@TableName("client_user")
public class ClientUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 登录账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 头像
	 */
	private String headImg;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 手机验证码
	 */
	private String phoneCode;
	/**
	 * 手机验证码更新时间
	 */
	private LocalDateTime phoneCodeTime;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别(0男 1女)
	 */
	private String sex;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 城市码（sys_area表tree_code）
	 */
	private String cityCode;
	/**
	 * 房东评分
	 */
	private BigDecimal score;
	/**
	 * 评论数量
	 */
	private Integer commentsNumber;
	/**
	 * 是否在线（0否 1是）
	 */
	private String isOnline;
	/**
	 * 是否实名认证（0否 1是）
	 */
	private String isCertified;
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 找回密码邮箱
	 */
	private String email;
	/**
	 * 邮箱验证码
	 */
	private String emailCode;
	/**
	 * 邮箱验证码更新时间
	 */
	private LocalDateTime emailCodeTime;
	/**
	 * 状态(0启用 1禁用)
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 是否删除(0未删除 1已删除）
	 */
	private String isDel;

}
