package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientHouseResidenceDao;
import com.admin.sys.entity.ClientHouseResidenceEntity;
import com.admin.sys.service.ClientHouseResidenceService;
import com.common.utils.page.PageData;;

@Service("clientHouseResidenceService")
public class ClientHouseResidenceServiceImpl extends ServiceImpl<ClientHouseResidenceDao, ClientHouseResidenceEntity> implements ClientHouseResidenceService {

}
