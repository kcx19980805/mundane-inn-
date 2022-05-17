package com.api.sys.service;

import com.api.sys.requestEntity.RequestUserAmountRecordDeleteEntity;
import com.api.sys.requestEntity.RequestUserAmountRecordListEntity;
import com.api.sys.responseEntity.ResponseUserAmountRecordListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.ClientUserAmountRecordEntity;
import com.common.utils.page.PageData;

import java.util.List;


/**
 * 用户余额变更记录表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface ClientUserAmountRecordService extends IService<ClientUserAmountRecordEntity> {

    /**
     * 用户余额变更记录表-列表
     * @param req
     * @return
     */
    PageData<ResponseUserAmountRecordListEntity> clientUserAmountRecordList(RequestUserAmountRecordListEntity req);

    /**
     * 用户余额变更记录表-删除
     * @param req
     * @return
     */
    int deleteAmountRecord(RequestUserAmountRecordDeleteEntity req);
}

