package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientAreaPopularDao;
import com.admin.sys.entity.ClientAreaPopularEntity;
import com.admin.sys.service.ClientAreaPopularService;
import com.common.utils.page.PageData;;

@Service("clientAreaPopularService")
public class ClientAreaPopularServiceImpl extends ServiceImpl<ClientAreaPopularDao, ClientAreaPopularEntity> implements ClientAreaPopularService {

}
