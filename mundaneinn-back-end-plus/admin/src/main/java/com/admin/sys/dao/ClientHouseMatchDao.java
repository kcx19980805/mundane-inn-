package com.admin.sys.dao;

import com.admin.sys.entity.ClientHouseMatchEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 房源配套关联表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientHouseMatchDao extends BaseMapper<ClientHouseMatchEntity> {

}
