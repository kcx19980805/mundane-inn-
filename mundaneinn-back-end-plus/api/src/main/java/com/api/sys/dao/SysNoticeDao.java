package com.api.sys.dao;

import com.api.sys.entity.SysNoticeEntity;
import com.api.sys.requestEntity.RequestNoticeDeleteEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 系统通知表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface SysNoticeDao extends BaseMapper<SysNoticeEntity> {
    /***
     * 系统通知表-列表
     * @return
     */
    List<SysNoticeEntity> sysNoticeList(String id);

    /***
     * 系统通知表-未读通知总数
     * @return
     */
    int sysNoticeListTotal(String id);

    /**
     * 系统通知表-假删除
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
