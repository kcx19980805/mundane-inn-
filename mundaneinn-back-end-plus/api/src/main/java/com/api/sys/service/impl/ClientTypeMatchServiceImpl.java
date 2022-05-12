package com.api.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import com.api.sys.dao.ClientTypeMatchDao;
import com.api.sys.entity.ClientTypeMatchEntity;
import com.api.sys.service.ClientTypeMatchService;

import java.util.ArrayList;

@Service("clientTypeMatchService")
public class ClientTypeMatchServiceImpl extends ServiceImpl<ClientTypeMatchDao, ClientTypeMatchEntity> implements ClientTypeMatchService {
    /**
    * 房源配套类型表-列表
    * @return
    */
    @Override
    public List<ClientTypeMatchEntity> clientTypeMatchList() {
        return baseMapper.clientTypeMatchList();
    }
}
