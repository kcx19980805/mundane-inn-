package com.api.sys.service.impl;

import com.api.sys.requestEntity.RequestAreaPopularListEntity;
import com.api.sys.responseEntity.ResponseAreaPopularListEntity;
import com.common.utils.PointUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;

import com.api.sys.dao.ClientAreaPopularDao;
import com.api.sys.entity.ClientAreaPopularEntity;
import com.api.sys.service.ClientAreaPopularService;

import java.util.ArrayList;

@Service("clientAreaPopularService")
public class ClientAreaPopularServiceImpl extends ServiceImpl<ClientAreaPopularDao, ClientAreaPopularEntity> implements ClientAreaPopularService {
    /**
    * 用户常用地区表-列表
    * @return
    */
    @Override
    public List<ResponseAreaPopularListEntity> clientAreaPopularList(RequestAreaPopularListEntity req) {
        List<ClientAreaPopularEntity> list = baseMapper.clientAreaPopularList(req);
        List<ResponseAreaPopularListEntity> resList= new ArrayList<>();
        double[] dest;
        for (ClientAreaPopularEntity item : list){
            ResponseAreaPopularListEntity res = new ResponseAreaPopularListEntity();
            res.setName(item.getName());
            if(item.getPoint()!= null){
                dest=PointUtils.bytesToPoints(item.getPoint());
                res.setPointLng(dest[0]+"");
                res.setPointLat(dest[1]+"");
            }
            resList.add(res);
        }
        return resList;
    }
}
