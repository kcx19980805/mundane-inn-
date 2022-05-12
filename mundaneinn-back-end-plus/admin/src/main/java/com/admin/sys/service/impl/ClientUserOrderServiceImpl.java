package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientUserOrderDao;
import com.admin.sys.entity.ClientUserOrderEntity;
import com.admin.sys.service.ClientUserOrderService;
import com.common.utils.page.PageData;;

@Service("clientUserOrderService")
public class ClientUserOrderServiceImpl extends ServiceImpl<ClientUserOrderDao, ClientUserOrderEntity> implements ClientUserOrderService {

}
