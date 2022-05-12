package com.api.sys.service.impl;

import com.api.sys.requestEntity.RequestUserAmountRecordDeleteEntity;
import com.api.sys.requestEntity.RequestUserAmountRecordListEntity;
import com.api.sys.responseEntity.ResponseUserAmountRecordListEntity;
import com.common.utils.page.PageData;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;

import com.api.sys.dao.ClientUserAmountRecordDao;
import com.api.sys.entity.ClientUserAmountRecordEntity;
import com.api.sys.service.ClientUserAmountRecordService;

@Service("clientUserAmountRecordService")
public class ClientUserAmountRecordServiceImpl extends ServiceImpl<ClientUserAmountRecordDao, ClientUserAmountRecordEntity> implements ClientUserAmountRecordService {

    /**
     * 用户余额变更记录表-列表
     * @param req
     * @return
     */
    @Override
    public PageData<ResponseUserAmountRecordListEntity> clientUserAmountRecordList(RequestUserAmountRecordListEntity req) {
        int total = baseMapper.clientUserAmountRecordListTotal(req);
        if(total<=req.getLimit()){
            req.setSqlPage(0);
        }
        List<ResponseUserAmountRecordListEntity> list = baseMapper.clientUserAmountRecordList(req);
        return PageData.ok(list,total);
    }

    /**
     * 用户余额变更记录表-删除
     * @param req
     * @return
     */
    @Override
    public int deleteAmountRecord(RequestUserAmountRecordDeleteEntity req) {
        ClientUserAmountRecordEntity entity = new ClientUserAmountRecordEntity();
        entity.setId(Long.valueOf(req.getId()));
        entity.setIsDel("1");
        return baseMapper.updateById(entity);
    }
}
