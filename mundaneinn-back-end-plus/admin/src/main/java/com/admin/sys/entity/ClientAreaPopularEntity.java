package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户常用地区表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("client_area_popular")
public class ClientAreaPopularEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 位置名称
	 */
	private String name;
	/**
	 * 所属地区分类id(client_area_type表id)
	 */
	private Long areaTypeId;
	/**
	 * 所属城市编码（sys_area表tree_code）
	 */
	private String treeCode;
	/**
	 * 经纬度坐标
	 */
	private byte[] point;
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
