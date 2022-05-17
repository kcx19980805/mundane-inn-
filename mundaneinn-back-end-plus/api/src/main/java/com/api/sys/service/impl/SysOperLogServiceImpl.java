package com.api.sys.service.impl;

import com.api.sys.dao.SysOperLogDao;
import com.api.sys.entity.SysOperLogEntity;
import com.api.sys.service.SysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 操作日志
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:14
 */
@Service("sysOperLogService")
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogDao, SysOperLogEntity> implements SysOperLogService {


}
