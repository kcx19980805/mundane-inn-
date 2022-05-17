package com.api.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 系统管理员
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 账号
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
	 * 所属管理组(存sys_permission_groupid)  0：超级管理员  (其余的根据权限组的id来决定)
	 */
	private Long type;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 状态  0：正常   1：禁用
	 */
	private String status;
	/**
	 * 是否删除  0正常,  1删除
	 */
	private String isDel;

}
