package com.api.sys.service;

import com.api.sys.requestEntity.RequestNoticeDeleteEntity;
import com.api.sys.responseEntity.ResponseNoticeListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.SysNoticeEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 系统通知表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface SysNoticeService extends IService<SysNoticeEntity> {
    /**
    * 系统通知表-列表
    * @return
    */
    ResponseNoticeListEntity sysNoticeList(String userId);

    /**
    * 系统通知表-假删除
    * @param req
    * @return
    */
    int deleteSysNoticeById(RequestNoticeDeleteEntity req);

    /**
    * 系统通知表-已读
    * @param req
    * @return
    */
    int sysNoticeIsRead(RequestNoticeDeleteEntity req);
}

