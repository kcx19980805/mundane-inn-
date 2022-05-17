package com.api.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 权限组关联菜单表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("sys_permission_group_menu")
public class SysPermissionGroupMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * sys_permission_group(权限组)表id
	 */
	private Long permissionGroupId;
	/**
	 * 菜单id
	 */
	private Long menuId;

}
