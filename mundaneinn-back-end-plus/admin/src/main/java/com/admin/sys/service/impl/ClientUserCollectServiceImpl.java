package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientUserCollectDao;
import com.admin.sys.entity.ClientUserCollectEntity;
import com.admin.sys.service.ClientUserCollectService;
import com.common.utils.page.PageData;;

@Service("clientUserCollectService")
public class ClientUserCollectServiceImpl extends ServiceImpl<ClientUserCollectDao, ClientUserCollectEntity> implements ClientUserCollectService {

}
