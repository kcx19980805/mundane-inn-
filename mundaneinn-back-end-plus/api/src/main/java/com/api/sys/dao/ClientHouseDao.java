package com.api.sys.dao;

import com.api.sys.entity.ClientHouseEntity;
import com.api.sys.requestEntity.RequestHouseInfoEntity;
import com.api.sys.requestEntity.RequestHouseListEntity;
import com.api.sys.requestEntity.RequestHouseUpdateEntity;
import com.api.sys.responseEntity.ResponseHouseInfoEntity;
import com.api.sys.responseEntity.ResponseHouseListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

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
     * 房东房源列表
     * @return
     */
    List<ResponseHouseListEntity> landlordHouseList(RequestHouseListEntity req);

    /**
     * 房东房源列表-总数
     * @return
     */
    Integer landlordHouseListTotal(RequestHouseListEntity req);


    /**
     * 房源表-单个信息
     * @param requestHouseInfoEntity
     * @return
     */
    ResponseHouseInfoEntity clientHouseInfo(RequestHouseInfoEntity requestHouseInfoEntity);

    /**
     * 新增房源
     * @param req
     * @return
     */
    int addHouse(RequestHouseUpdateEntity req);

    /**
     * 修改房源
     * @param req
     * @return
     */
    int updateHouse(RequestHouseUpdateEntity req);

    /**
     * 查询房源详细
     * @param houseId
     * @return
     */
    ClientHouseEntity getHouseById(String houseId);
}
