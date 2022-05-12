package com.api.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import com.api.sys.dao.SysPermissionGroupDao;
import com.api.sys.entity.SysPermissionGroupEntity;
import com.api.sys.service.SysPermissionGroupService;

import java.util.ArrayList;

@Service("sysPermissionGroupService")
public class SysPermissionGroupServiceImpl extends ServiceImpl<SysPermissionGroupDao, SysPermissionGroupEntity> implements SysPermissionGroupService {

}
