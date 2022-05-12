package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientHouseMatchDao;
import com.admin.sys.entity.ClientHouseMatchEntity;
import com.admin.sys.service.ClientHouseMatchService;
import com.common.utils.page.PageData;;

@Service("clientHouseMatchService")
public class ClientHouseMatchServiceImpl extends ServiceImpl<ClientHouseMatchDao, ClientHouseMatchEntity> implements ClientHouseMatchService {

}
