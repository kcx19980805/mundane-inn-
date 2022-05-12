package com.api.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import com.api.sys.dao.ClientHousePictureDao;
import com.api.sys.entity.ClientHousePictureEntity;
import com.api.sys.service.ClientHousePictureService;

import java.util.ArrayList;

@Service("clientHousePictureService")
public class ClientHousePictureServiceImpl extends ServiceImpl<ClientHousePictureDao, ClientHousePictureEntity> implements ClientHousePictureService {

}
