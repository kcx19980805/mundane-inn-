package com.api.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import com.api.sys.dao.ClientAreaTypeDao;
import com.api.sys.entity.ClientAreaTypeEntity;
import com.api.sys.service.ClientAreaTypeService;

import java.util.ArrayList;

@Service("clientAreaTypeService")
public class ClientAreaTypeServiceImpl extends ServiceImpl<ClientAreaTypeDao, ClientAreaTypeEntity> implements ClientAreaTypeService {
    /**
    * 地区分类表-列表
    * @return
    */
    @Override
    public List<ClientAreaTypeEntity> clientAreaTypeList() {
        return baseMapper.clientAreaTypeList();
    }
}
