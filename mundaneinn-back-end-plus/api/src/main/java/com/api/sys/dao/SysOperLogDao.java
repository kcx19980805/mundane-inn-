package com.api.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.api.sys.entity.SysOperLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Mapper
public interface SysOperLogDao extends BaseMapper<SysOperLogEntity> {

}
