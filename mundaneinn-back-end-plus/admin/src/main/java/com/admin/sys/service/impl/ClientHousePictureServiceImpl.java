package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientHousePictureDao;
import com.admin.sys.entity.ClientHousePictureEntity;
import com.admin.sys.service.ClientHousePictureService;
import com.common.utils.page.PageData;;

@Service("clientHousePictureService")
public class ClientHousePictureServiceImpl extends ServiceImpl<ClientHousePictureDao, ClientHousePictureEntity> implements ClientHousePictureService {

}
