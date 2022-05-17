package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 房源照片关联表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("client_house_picture")
public class ClientHousePictureEntity implements Serializable {
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
	 * 房源图片
	 */
	private String imgUrl;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

}
