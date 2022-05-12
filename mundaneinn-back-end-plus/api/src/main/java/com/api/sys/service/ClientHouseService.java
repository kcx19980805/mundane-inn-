package com.api.sys.service;

import com.api.sys.requestEntity.RequestHouseUpdateEntity;
import com.api.sys.requestEntity.RequestHouseInfoEntity;
import com.api.sys.requestEntity.RequestHouseListEntity;
import com.api.sys.responseEntity.ResponseHouseInfoEntity;
import com.api.sys.responseEntity.ResponseHouseListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.ClientHouseEntity;
import com.common.utils.page.PageData;


/**
 * 房源表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface ClientHouseService extends IService<ClientHouseEntity> {

    /**
     * 房源表-列表
     * @param req
     * @return
     */
    PageData<ResponseHouseListEntity> clientHouseList(RequestHouseListEntity req);

    /**
    * 房源表-单个信息
    * @param req
    * @return
    */
    ResponseHouseInfoEntity clientHouseInfo(RequestHouseInfoEntity req);

    /**
     * 房东房源列表
     * @param req
     * @return
     */
    PageData<ResponseHouseListEntity> landlordHouseList(RequestHouseListEntity req);


    /**
     * 房源表-新增
     * @param req
     * @return
     */
    int saveClientHouse(RequestHouseUpdateEntity req);

    /**
    * 房源表-删除
    * @param req
    * @return
    */
    int deleteClientHouseById(RequestHouseInfoEntity req);

    /**
    * 房源表-修改
    * @param req
    * @return
    */
    int updateClientHouse(RequestHouseUpdateEntity req);

    /**
     * 查询房源详细
     * @param houseId
     * @return
     */
    ClientHouseEntity getHouseById(String houseId);
}

