package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.SysAreaDao;
import com.admin.sys.entity.SysAreaEntity;
import com.admin.sys.service.SysAreaService;
import com.common.utils.page.PageData;;

@Service("sysAreaService")
public class SysAreaServiceImpl extends ServiceImpl<SysAreaDao, SysAreaEntity> implements SysAreaService {

}
