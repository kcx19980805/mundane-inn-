package com.admin.sys.dao;

import com.admin.sys.entity.ClientHouseEntity;
import com.admin.sys.requestEntity.RequestHouseListEntity;
import com.admin.sys.responseEntity.ResponseHouseListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 房源表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientHouseDao extends BaseMapper<ClientHouseEntity> {
    /**
     * 房源表-列表
     * @return
     */
    List<ResponseHouseListEntity> clientHouseList(RequestHouseListEntity req);

    /**
     * 房源表-列表-总数
     * @return
     */
    Integer clientHouseListTotal(RequestHouseListEntity req);

    /**
     * 查询房源详细
     * @param houseId
     * @return
     */
    ClientHouseEntity getHouseById(String houseId);
}
