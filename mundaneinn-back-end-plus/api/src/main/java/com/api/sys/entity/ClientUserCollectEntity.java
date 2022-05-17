package com.api.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户收藏表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("client_user_collect")
public class ClientUserCollectEntity implements Serializable {
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
	 * 房源id（client_house表id）
	 */
	private Long houseId;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

}
