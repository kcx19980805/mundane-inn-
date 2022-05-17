package com.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 全国地区编码表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("sys_area")
public class SysAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 地区编码，2位数代表省/直辖市，4位数代表市，6位数代表县/区
	 */
	private String treeCode;
	/**
	 * 地区名称
	 */
	private String name;
	/**
	 * 地区全名
	 */
	private String fullName;

}
