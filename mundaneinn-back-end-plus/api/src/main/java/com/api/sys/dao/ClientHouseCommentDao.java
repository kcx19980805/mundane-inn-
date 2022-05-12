package com.api.sys.dao;

import com.api.sys.entity.ClientHouseCommentEntity;
import com.api.sys.requestEntity.RequestHouseCommentListEntity;
import com.api.sys.responseEntity.ResponseHouseCommentListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 房源评论表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientHouseCommentDao extends BaseMapper<ClientHouseCommentEntity> {
    /***
     * 房源评论表-列表
     * @return
     */
    List<ResponseHouseCommentListEntity> clientHouseCommentList(RequestHouseCommentListEntity req);

    /***
     * 房源评论表-列表-总数
     * @return
     */
    Integer clientHouseCommentListTotal(RequestHouseCommentListEntity req);


    /**
     * 根据订单id查询房东id
     * @param orderId
     * @return
     */
    Long getUserIdByOrderId(String orderId);
}
