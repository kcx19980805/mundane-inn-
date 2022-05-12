package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientHouseCommentDao;
import com.admin.sys.entity.ClientHouseCommentEntity;
import com.admin.sys.service.ClientHouseCommentService;
import com.common.utils.page.PageData;;

@Service("clientHouseCommentService")
public class ClientHouseCommentServiceImpl extends ServiceImpl<ClientHouseCommentDao, ClientHouseCommentEntity> implements ClientHouseCommentService {

}
