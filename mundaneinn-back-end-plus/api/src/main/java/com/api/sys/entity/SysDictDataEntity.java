package com.api.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;


/**
 * 系统字典数据表
 *
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Data
@TableName("sys_dict_data")
public class SysDictDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 字典排序
	 */
	private Integer dictSort;
	/**
	 * 字典标签
	 */
	private String dictLabel;
	/**
	 * 字典键值
	 */
	private String dictValue;
	/**
	 * 字典类型
	 */
	private String dictType;
	/**
	 * 是否默认（0是 1否）
	 */
	private String isDefault;
	/**
	 * 状态（0正常 1停用）
	 */
	private String status;
	/**
	 * 创建者id(sys_user表id)
	 */
	private Long createUserId;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新者id(sys_user表id)
	 */
	private Long updateUserId;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
