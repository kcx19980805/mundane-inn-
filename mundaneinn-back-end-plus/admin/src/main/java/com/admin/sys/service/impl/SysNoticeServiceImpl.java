package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.SysNoticeDao;
import com.admin.sys.entity.SysNoticeEntity;
import com.admin.sys.service.SysNoticeService;
import com.common.utils.page.PageData;;

@Service("sysNoticeService")
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeDao, SysNoticeEntity> implements SysNoticeService {

}
