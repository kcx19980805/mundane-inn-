package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientTypeResidenceDao;
import com.admin.sys.entity.ClientTypeResidenceEntity;
import com.admin.sys.service.ClientTypeResidenceService;
import com.common.utils.page.PageData;;

@Service("clientTypeResidenceService")
public class ClientTypeResidenceServiceImpl extends ServiceImpl<ClientTypeResidenceDao, ClientTypeResidenceEntity> implements ClientTypeResidenceService {

}
