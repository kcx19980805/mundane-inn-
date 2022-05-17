package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientUserAmountRecordDao;
import com.admin.sys.entity.ClientUserAmountRecordEntity;
import com.admin.sys.service.ClientUserAmountRecordService;
import com.common.utils.page.PageData;;

@Service("clientUserAmountRecordService")
public class ClientUserAmountRecordServiceImpl extends ServiceImpl<ClientUserAmountRecordDao, ClientUserAmountRecordEntity> implements ClientUserAmountRecordService {

}
