package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientAreaTypeDao;
import com.admin.sys.entity.ClientAreaTypeEntity;
import com.admin.sys.service.ClientAreaTypeService;
import com.common.utils.page.PageData;;

@Service("clientAreaTypeService")
public class ClientAreaTypeServiceImpl extends ServiceImpl<ClientAreaTypeDao, ClientAreaTypeEntity> implements ClientAreaTypeService {

}
