package com.api.sys.dao;

import com.api.sys.entity.SysPermissionGroupMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 权限组关联菜单表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface SysPermissionGroupMenuDao extends BaseMapper<SysPermissionGroupMenuEntity> {

}
