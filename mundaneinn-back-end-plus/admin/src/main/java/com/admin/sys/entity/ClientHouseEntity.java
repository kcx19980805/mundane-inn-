package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;


/**
 * 房源表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("client_house")
public class ClientHouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 房东id（client_user表id）
	 */
	private Long userId;
	/**
	 * 房源名称
	 */
	private String name;
	/**
	 * 详细位置（门牌号）
	 */
	private String location;
	/**
	 * 所属城市编码（sys_area表tree_code）
	 */
	private String treeCode;
	/**
	 * 经纬度坐标
	 */
	private byte[] point;
	/**
	 * 房源价格
	 */
	private BigDecimal housePrice;
	/**
	 * 折扣
	 */
	private String discount;
	/**
	 * 房型（0小区住宅1公寓）
	 */
	private String residenceType;
	/**
	 * 开始出租日期
	 */
	private LocalDateTime startDate;
	/**
	 * 结束出租日期
	 */
	private LocalDateTime endDate;
	/**
	 * 出租类型（0单间1整套）
	 */
	private String rentalType;
	/**
	 * 人数1-10,10表示10人及以上
	 */
	private Integer peopleNumber;
	/**
	 * 床数1-10,10表示10人及以上
	 */
	private Integer bedNumber;
	/**
	 * 户型1-4,4表示4户及以上
	 */
	private Integer houseType;
	/**
	 * 客厅数量
	 */
	private Integer parlorNumber;
	/**
	 * 卫生间数量
	 */
	private Integer bathroomNumber;
	/**
	 * 厨房数量
	 */
	private Integer kitchenNumber;
	/**
	 * 房源描述
	 */
	private String describe;
	/**
	 * 是否近地铁0是1否
	 */
	private String isNearSubway;
	/**
	 * 状态0=待审核1=已上架2=审核失败3=已下架
	 */
	private Integer state;
	/**
	 * 审核失败原因
	 */
	private String reason;
	/**
	 * 综合评分
	 */
	private BigDecimal score;
	/**
	 * 评论数量
	 */
	private Integer commentsNumber;
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
