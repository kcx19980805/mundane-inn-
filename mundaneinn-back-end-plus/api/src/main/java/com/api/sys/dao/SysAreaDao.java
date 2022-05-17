package com.api.sys.dao;

import com.api.sys.entity.SysAreaEntity;
import com.api.sys.requestEntity.RequestCityListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 全国地区编码表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface SysAreaDao extends BaseMapper<SysAreaEntity> {
    /***
     * 查询全部城市
     * @return
     */
    List<SysAreaEntity> getAllCity(RequestCityListEntity req);

    /**
     * 通过城市名称查找编码
     * @param cityName
     * @return
     */
    String getTreeCodeByCityName(String cityName);
}
