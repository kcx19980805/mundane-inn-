package com.api.sys.dao;

import com.api.sys.entity.ClientUserCollectEntity;
import com.api.sys.responseEntity.ResponseCollectOtherListEntity;
import com.api.sys.responseEntity.ResponseHouseListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 用户收藏表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientUserCollectDao extends BaseMapper<ClientUserCollectEntity> {

    /**
     * 用户收藏列表
     * @param userId
     * @return
     */
    List<ResponseHouseListEntity> clientUserCollectList(String userId);

    /**
     * 查询其他用户的收藏房源
     * @param userId
     * @return
     */
    List<ResponseCollectOtherListEntity> getOtherUserCollectList(String userId);
}
