package com.admin.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.SysDictDataDao;
import com.admin.sys.entity.SysDictDataEntity;
import com.admin.sys.service.SysDictDataService;
import com.common.utils.page.PageData;;

@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataDao, SysDictDataEntity> implements SysDictDataService {

}
