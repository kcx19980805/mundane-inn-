package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientUserChatDao;
import com.admin.sys.entity.ClientUserChatEntity;
import com.admin.sys.service.ClientUserChatService;
import com.common.utils.page.PageData;;

@Service("clientUserChatService")
public class ClientUserChatServiceImpl extends ServiceImpl<ClientUserChatDao, ClientUserChatEntity> implements ClientUserChatService {

}
