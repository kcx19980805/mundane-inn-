package com.api.sys.dao;

import com.api.sys.entity.ClientTypeResidenceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 房源房型类型表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientTypeResidenceDao extends BaseMapper<ClientTypeResidenceEntity> {
    /***
     * 房源房型类型表-列表
     * @return
     */
    List<ClientTypeResidenceEntity> clientTypeResidenceList();

}
