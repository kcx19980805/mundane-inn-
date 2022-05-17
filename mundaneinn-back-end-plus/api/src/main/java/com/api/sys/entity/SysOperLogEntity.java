package com.api.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志记录
 *
 * @author
 * @email
 * @date 2020-12-18 00:55:01
 */
@Data
@TableName("sys_oper_log")
public class SysOperLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 日志主键
	 */
	@TableId
	private Long id;
	/**
	 * 模块标题
	 */
	private String title;
	/**
	 * 操作人员账号id
	 */
	private Long operUserId;
	/**
	 * 操作人员账号
	 */
	private String operUserAccount;
	/**
	 * 操作人员类型(0平台 1分公司 2厂家 3用户)
	 */
	private String operUserType;
	/**
	 * 主机地址ip
	 */
	private String operIp;
	/**
	 * 请求参数
	 */
	private String operParam;
	/**
	 * 返回参数
	 */
	private String jsonResult;
	/**
	 * 操作时长(毫秒)
	 */
	private Long operTime;
	/**
	 * 操作状态（0正常/成功 1异常/错误）
	 */
	private Integer status;
	/**
	 * 错误消息
	 */
	private String errorMsg;
	/**
	 * 操作时间
	 */
	private LocalDateTime createTime;

}
