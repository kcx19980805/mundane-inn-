package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户订单表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("client_user_order")
public class ClientUserOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 房源id（client_house表id）
	 */
	private Long houseId;
	/**
	 * 房源名称
	 */
	private String name;
	/**
	 * 详细位置（门牌号）
	 */
	private String location;
	/**
	 * 用户id（client_user表id）
	 */
	private Long userId;
	/**
	 * 开始日期
	 */
	private LocalDateTime startDate;
	/**
	 * 结束日期
	 */
	private LocalDateTime endDate;
	/**
	 * 房源单日价格
	 */
	private BigDecimal housePrice;
	/**
	 * 房东折扣（暂时不用）
	 */
	private String discountLandlord;
	/**
	 * 房东折扣后的结算金额（暂时不用）
	 */
	private BigDecimal amount;
	/**
	 * 系统折扣（暂时不用）
	 */
	private String discountSys;
	/**
	 * 用户红包抵扣（暂时不用）
	 */
	private BigDecimal redEnvelope;
	/**
	 * 实际支付金额
	 */
	private BigDecimal amountActual;
	/**
	 * 订单状态（0待支付1待入住2已完成3已取消4已退款）
	 */
	private Integer state;
	/**
	 * 支付时间
	 */
	private LocalDateTime payTime;
	/**
	 * 支付宝订单号
	 */
	private String orderNumber;
	/**
	 * 支付宝支付状态(0支付失败 1支付成功)
	 */
	private String paymentStatus;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 用户是否删除(0未删除 1已删除）
	 */
	private String isDelUser;
	/**
	 * 房东是否删除(0未删除 1已删除）
	 */
	private String isDelLandlord;

}
