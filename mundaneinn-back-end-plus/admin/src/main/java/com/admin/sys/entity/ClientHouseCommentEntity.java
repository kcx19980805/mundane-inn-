package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;


/**
 * 房源评论表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("client_house_comment")
public class ClientHouseCommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 用户id（client_user表id）
	 */
	private Long userId;
	/**
	 * 房东id（client_user表id）
	 */
	private Long landlordId;
	/**
	 * 房源id（client_house表id）
	 */
	private Long houseId;
	/**
	 * 订单id（client_user_order表id）
	 */
	private Long orderId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论星级
	 */
	private BigDecimal score;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 是否删除(0未删除 1已删除）
	 */
	private String isDel;

}
