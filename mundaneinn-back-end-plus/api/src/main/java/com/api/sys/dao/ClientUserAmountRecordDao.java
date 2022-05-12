package com.api.sys.dao;

import com.api.sys.entity.ClientUserAmountRecordEntity;
import com.api.sys.requestEntity.RequestUserAmountRecordListEntity;
import com.api.sys.responseEntity.ResponseUserAmountRecordListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 用户余额变更记录表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientUserAmountRecordDao extends BaseMapper<ClientUserAmountRecordEntity> {
    /***
     * 用户余额变更记录表-列表
     * @return
     */
    List<ResponseUserAmountRecordListEntity> clientUserAmountRecordList(RequestUserAmountRecordListEntity req);

    /***
     * 用户余额变更记录表-列表-总数
     * @return
     */
    Integer clientUserAmountRecordListTotal(RequestUserAmountRecordListEntity req);
}
