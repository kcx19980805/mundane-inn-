package com.api.sys.service;

import com.api.sys.requestEntity.RequestCityListEntity;
import com.api.sys.responseEntity.ResponseCityListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.SysAreaEntity;
import java.util.List;
import java.util.Map;


/**
 * 全国地区编码表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface SysAreaService extends IService<SysAreaEntity> {
    /**
    * 查询全部城市-列表
    * @return
    */
    List<ResponseCityListEntity> sysAreaList(RequestCityListEntity req);

    /**
     * 通过城市名称查找编码
     * @param cityName
     * @return
     */
    String getTreeCodeByCityName(String cityName);
}

