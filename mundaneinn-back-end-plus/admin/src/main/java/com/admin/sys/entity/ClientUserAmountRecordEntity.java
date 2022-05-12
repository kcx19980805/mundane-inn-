package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户余额变更记录表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("client_user_amount_record")
public class ClientUserAmountRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 订单id（client_user_order表id）
	 */
	private Long orderId;
	/**
	 * 用户id（client_user表id）
	 */
	private Long userId;
	/**
	 * 操作符（+或-）
	 */
	private String operator;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 原因（用户下单/平台扣款）
	 */
	private String reson;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 是否删除(0未删除 1已删除）
	 */
	private String isDel;

}
