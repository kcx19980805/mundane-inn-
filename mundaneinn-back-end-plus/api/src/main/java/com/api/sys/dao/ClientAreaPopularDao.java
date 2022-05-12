package com.api.sys.dao;

import com.api.sys.entity.ClientAreaPopularEntity;
import com.api.sys.requestEntity.RequestAreaPopularListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 用户常用地区表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientAreaPopularDao extends BaseMapper<ClientAreaPopularEntity> {
    /***
     * 用户常用地区表-列表
     * @return
     */
    List<ClientAreaPopularEntity> clientAreaPopularList(RequestAreaPopularListEntity req);

}
