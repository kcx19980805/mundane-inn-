package com.admin.sys.service;

import com.admin.sys.requestEntity.RequestHouseListEntity;
import com.admin.sys.requestEntity.RequestHouseUpdateEntity;
import com.admin.sys.responseEntity.ResponseHouseListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.admin.sys.entity.ClientHouseEntity;
import com.common.utils.page.PageData;;

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
     * 房源表-审核
     * @param req
     * @return
     */
    int updateClientHouse(RequestHouseUpdateEntity req);
}

