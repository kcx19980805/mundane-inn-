package com.api.sys.service.impl;

import com.api.sys.requestEntity.RequestNoticeDeleteEntity;
import com.api.sys.responseEntity.ResponseNoticeListEntity;
import com.common.constant.Constants;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import com.api.sys.dao.SysNoticeDao;
import com.api.sys.entity.SysNoticeEntity;
import com.api.sys.service.SysNoticeService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service("sysNoticeService")
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeDao, SysNoticeEntity> implements SysNoticeService {

    /**
    * 系统通知表-列表
    * @return
    */
    @Override
    public ResponseNoticeListEntity sysNoticeList(String userId) {
        List<SysNoticeEntity> sysNoticeEntityList = baseMapper.sysNoticeList(userId);
        int total = baseMapper.sysNoticeListTotal(userId);
        ResponseNoticeListEntity responseNoticeListEntity = new ResponseNoticeListEntity();
        responseNoticeListEntity.setList(sysNoticeEntityList);
        responseNoticeListEntity.setUnReadTotal(total);
        return responseNoticeListEntity;
    }

    /**
     * 系统通知表-假删除
     * @param req
     * @return
     */
    @Override
    public int deleteSysNoticeById(RequestNoticeDeleteEntity req) {
        return baseMapper.deleteSysNoticeById(req);
    }

    /**
     * 系统通知表-已读
     * @param req
     * @return
     */
    @Override
    public int sysNoticeIsRead(RequestNoticeDeleteEntity req) {
        //如果没有通知
        if(req.getNoticeIds()==null || req.getNoticeIds().size()==0){
            return 1;
        }
        return baseMapper.sysNoticeIsRead(req);
    }
}
