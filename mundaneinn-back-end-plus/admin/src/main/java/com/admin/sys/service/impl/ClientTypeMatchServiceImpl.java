package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientTypeMatchDao;
import com.admin.sys.entity.ClientTypeMatchEntity;
import com.admin.sys.service.ClientTypeMatchService;
import com.common.utils.page.PageData;;

@Service("clientTypeMatchService")
public class ClientTypeMatchServiceImpl extends ServiceImpl<ClientTypeMatchDao, ClientTypeMatchEntity> implements ClientTypeMatchService {

}
