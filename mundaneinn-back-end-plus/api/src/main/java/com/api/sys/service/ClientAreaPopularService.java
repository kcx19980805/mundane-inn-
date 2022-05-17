package com.api.sys.service;

import com.api.sys.requestEntity.RequestAreaPopularListEntity;
import com.api.sys.responseEntity.ResponseAreaPopularListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.ClientAreaPopularEntity;
import java.util.List;


/**
 * 用户常用地区表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface ClientAreaPopularService extends IService<ClientAreaPopularEntity> {
    /**
    * 用户常用地区表-列表
    * @return
    */
    List<ResponseAreaPopularListEntity> clientAreaPopularList(RequestAreaPopularListEntity req);

}

