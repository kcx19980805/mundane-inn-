package com.admin.sys.service.impl;

import com.admin.sys.entity.SysNoticeEntity;
import com.admin.sys.requestEntity.RequestHouseListEntity;
import com.admin.sys.requestEntity.RequestHouseUpdateEntity;
import com.admin.sys.responseEntity.ResponseClientUserListEntity;
import com.admin.sys.responseEntity.ResponseHouseListEntity;
import com.admin.sys.service.SysNoticeService;
import com.common.utils.recommend.DataModel;
import com.common.utils.recommend.Recommend;
import com.common.utils.recommend.UserCF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sys.dao.ClientHouseDao;
import com.admin.sys.entity.ClientHouseEntity;
import com.admin.sys.service.ClientHouseService;
import com.common.utils.page.PageData;;import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("clientHouseService")
public class ClientHouseServiceImpl extends ServiceImpl<ClientHouseDao, ClientHouseEntity> implements ClientHouseService {
    @Autowired
    private SysNoticeService sysNoticeService;
    /**
     * 房源表-列表
     * @param req
     * @return
     */
    @Override
    public PageData<ResponseHouseListEntity> clientHouseList(RequestHouseListEntity req) {
        Integer total = baseMapper.clientHouseListTotal(req);
        List<ResponseHouseListEntity> list= new ArrayList<>();
        if (total > 0) {
            list = baseMapper.clientHouseList(req);
            //每页大小
            int limit = req.getLimit();
            if (list.size() > 0 && limit > 0) {
                //当前页码 从0开始
                int currPage = req.getPage();
                //排序 asc升序 desc降序 转小写
                String order = req.getOrder().toLowerCase();
                for (int i = 0; i < list.size(); i++) {
                    //自动生成序号 字段:iid
                    list.get(i).setIid("asc".equals(order) ? limit * currPage + i + 1 : total - (limit * currPage) - i);
                }
            }
        }
        return PageData.ok(list, total);
    }

    /**
     * 房源表-审核
     * @param req
     * @return
     */
    @Override
    public int updateClientHouse(RequestHouseUpdateEntity req) {
        ClientHouseEntity clientHouseEntity = new ClientHouseEntity();
        clientHouseEntity.setId(Long.valueOf(req.getId()));
        clientHouseEntity.setState(Integer.valueOf(req.getState()));
        clientHouseEntity.setReason(req.getReason());
        //发送通知给用户
        ClientHouseEntity entity =baseMapper.getHouseById(req.getId());
        SysNoticeEntity sysNoticeEntity = new SysNoticeEntity();
        sysNoticeEntity.setSendType("0");
        sysNoticeEntity.setUserId(entity.getUserId());
        sysNoticeEntity.setNoticeType("0");
        if("1".equals(req.getState())){
            sysNoticeEntity.setContent("您的房源\""+entity.getName()+"\"已审核通过");
        }
        else if("2".equals(req.getState())){
            sysNoticeEntity.setContent("您的房源\""+entity.getName()+"\"审核未通过,原因："+req.getReason());
        }
        sysNoticeService.save(sysNoticeEntity);
        return baseMapper.updateById(clientHouseEntity);
    }
}
