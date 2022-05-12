package com.api.sys.service;

import com.api.sys.responseEntity.ResponseCollectOtherListEntity;
import com.api.sys.responseEntity.ResponseHouseListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.ClientUserCollectEntity;
import java.util.List;


/**
 * 用户收藏表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface ClientUserCollectService extends IService<ClientUserCollectEntity> {
    /**
    * 用户收藏列表
    * @return
    */
    List<ResponseHouseListEntity> clientUserCollectList(String userId);

    /**
     * 查询其他用户的收藏房源
     * @param userId
     * @return
     */
    List<ResponseCollectOtherListEntity> getOtherUserCollectList(String userId);

    /**
     * 用户收藏表-新增
     * @param userId
     * @param houseId
     * @return
     */
    int saveClientUserCollect(String userId,String houseId);

    /**
     * 用户收藏表-删除
     * @param userId
     * @param houseId
     * @return
     */
    int deleteClientUserCollectById(String userId,String houseId);

}

