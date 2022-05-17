package com.api.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import com.api.sys.dao.ClientHouseMatchDao;
import com.api.sys.entity.ClientHouseMatchEntity;
import com.api.sys.service.ClientHouseMatchService;

import java.util.ArrayList;

@Service("clientHouseMatchService")
public class ClientHouseMatchServiceImpl extends ServiceImpl<ClientHouseMatchDao, ClientHouseMatchEntity> implements ClientHouseMatchService {

}
