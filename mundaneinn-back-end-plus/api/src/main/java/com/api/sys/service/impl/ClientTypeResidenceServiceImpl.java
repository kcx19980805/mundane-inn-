package com.api.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import com.api.sys.dao.ClientTypeResidenceDao;
import com.api.sys.entity.ClientTypeResidenceEntity;
import com.api.sys.service.ClientTypeResidenceService;

import java.util.ArrayList;

@Service("clientTypeResidenceService")
public class ClientTypeResidenceServiceImpl extends ServiceImpl<ClientTypeResidenceDao, ClientTypeResidenceEntity> implements ClientTypeResidenceService {
    /**
    * 房源房型类型表-列表
    * @return
    */
    @Override
    public List<ClientTypeResidenceEntity> clientTypeResidenceList() {
        return baseMapper.clientTypeResidenceList();
    }

}
