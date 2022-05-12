package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 地区分类表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("client_area_type")
public class ClientAreaTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 分类名称
	 */
	private String typeName;
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
