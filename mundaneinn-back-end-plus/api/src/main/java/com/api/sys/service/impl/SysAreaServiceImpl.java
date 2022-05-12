package com.api.sys.service.impl;

import com.api.sys.requestEntity.RequestCityListEntity;
import com.api.sys.responseEntity.ResponseCityListEntity;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;
import com.api.sys.dao.SysAreaDao;
import com.api.sys.entity.SysAreaEntity;
import com.api.sys.service.SysAreaService;

@Service("sysAreaService")
public class SysAreaServiceImpl extends ServiceImpl<SysAreaDao, SysAreaEntity> implements SysAreaService {
    /**
    * 查询全部城市-列表
    * @return
    */
    @Override
    public List<ResponseCityListEntity>  sysAreaList(RequestCityListEntity req) {
        List<ResponseCityListEntity> responseCityListEntities = new ArrayList<>();
        List<SysAreaEntity> list =baseMapper.getAllCity(req);
        if("1".equals(req.getOrderByPingYin())){
            String[] strArray= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
            String str="abcdefghijklmnopqrstuvwxyz";
            for (int i = 0; i < strArray.length; i++) {
                ResponseCityListEntity cityListEntity = new ResponseCityListEntity();
                cityListEntity.setPingYin(strArray[i]);
                cityListEntity.setList(new ArrayList<>());
                responseCityListEntities.add(cityListEntity);
            }
            Iterator<SysAreaEntity> iterator = list.iterator();
            while(iterator.hasNext()){
                SysAreaEntity city=iterator.next();
                String first=PinyinHelper.toHanyuPinyinStringArray(city.getName().charAt(0))[0].charAt(0)+"";
                int index=str.indexOf(first);
                responseCityListEntities.get(index).getList().add(city);
            }
        }else {
            ResponseCityListEntity res=new ResponseCityListEntity();
            res.setPingYin("");
            res.setList(list);
            responseCityListEntities.add(res);
        }
        return responseCityListEntities;
    }

    /**
     * 通过城市名称查找编码
     * @param cityName
     * @return
     */
    @Override
    public String getTreeCodeByCityName(String cityName) {
        return baseMapper.getTreeCodeByCityName(cityName);
    }
}
