package com.api.sys.service.impl;

import com.api.sys.entity.*;
import com.api.sys.requestEntity.RequestHouseUpdateEntity;
import com.api.sys.requestEntity.RequestHouseInfoEntity;
import com.api.sys.requestEntity.RequestHouseListEntity;
import com.api.sys.responseEntity.ResponseHouseInfoEntity;
import com.api.sys.responseEntity.ResponseHouseListEntity;
import com.api.sys.responseEntity.ResponseCollectOtherListEntity;
import com.api.sys.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.exception.CustomException;
import com.common.utils.PointUtils;
import com.common.utils.date.DateUtils;
import com.common.utils.page.PageData;
import com.common.utils.recommend.DataModel;
import com.common.utils.recommend.Recommend;
import com.common.utils.recommend.UserCF;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import com.api.sys.dao.ClientHouseDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service("clientHouseService")
public class ClientHouseServiceImpl extends ServiceImpl<ClientHouseDao, ClientHouseEntity> implements ClientHouseService {
    @Autowired
    @Lazy
    private ClientUserCollectService clientUserCollectService;

    @Autowired
    @Lazy
    private ClientHouseMatchService clientHouseMatchService;

    @Autowired
    @Lazy
    private ClientHouseResidenceService clientHouseResidenceService;

    @Autowired
    @Lazy
    private ClientHousePictureService clientHousePictureService;

    @Autowired
    @Lazy
    private ClientUserOrderService clientUserOrderService;

    @Autowired
    @Lazy
    private SysAreaService sysAreaService;
    /**
     * 房源表-列表
     * @param req
     * @return
     */
    @Override
    public PageData<ResponseHouseListEntity> clientHouseList(RequestHouseListEntity req) {
        int matchListTotal=null==req.getMatchList()?0:req.getMatchList().size();
        int residenceListTotal=null==req.getResidenceList()?0:req.getResidenceList().size();
        req.setMatchListTotal(matchListTotal);
        req.setResidenceListTotal(residenceListTotal);
        req.setDistance(15);
        if("1".equals(req.getIsRecommend())){
            //查询当前用户的收藏
            List<ResponseHouseListEntity> collectList=clientUserCollectService.clientUserCollectList(req.getUserId());
            List<DataModel> userList = new ArrayList<>();
            for(ResponseHouseListEntity collectEntity : collectList){
                DataModel houseId=new DataModel(Long.valueOf(collectEntity.getId()));
                userList.add(houseId);
            }
            UserCF userCF = new UserCF(Long.valueOf(req.getUserId()),userList);
            System.err.println("=======当前用户======");
            System.err.println(userCF);
            //查询其它用户的收藏
            List<ResponseCollectOtherListEntity> collectOtherList=clientUserCollectService.getOtherUserCollectList(req.getUserId());
            List<UserCF> otherUserCF = new ArrayList<>();
            for(ResponseCollectOtherListEntity otherListEntity :collectOtherList){
                List<DataModel> otherDataModel=otherListEntity.getHouseIds().stream()
                        .map(item-> new DataModel(item))
                        .collect(Collectors.toList());
                UserCF otherCF = new UserCF(otherListEntity.getUserId(),otherDataModel);
                otherUserCF.add(otherCF);
                System.err.println("=======其它用户======");
                System.err.println(otherCF);
            }
            //计算推荐的房源id
            Recommend recommend = new Recommend();
            List<DataModel> res = recommend.results(userCF, otherUserCF);
            System.err.println("=======推荐房源======");
            System.err.println(res);
            if(res==null || res.size()==0){
                return PageData.ok(new ArrayList<>(),0);
            }
            //选出推荐房源
            req.setRecommendHouseIds(res.stream().map(item-> item.getDataId()).collect(Collectors.toList()));
        }
        Integer total = baseMapper.clientHouseListTotal(req);
        if(total<=req.getLimit()){
            req.setSqlPage(0);
        }
        List<ResponseHouseListEntity> list= baseMapper.clientHouseList(req);
        return PageData.ok(list,total);
    }

    /**
     * 房源表-单个信息
     * @param req
     * @return
     */
    @Override
    public ResponseHouseInfoEntity clientHouseInfo(RequestHouseInfoEntity req) {
        ResponseHouseInfoEntity res = baseMapper.clientHouseInfo(req);
        SysAreaEntity sysAreaEntity =sysAreaService.getOne(new QueryWrapper<SysAreaEntity>().eq("tree_code",res.getTreeCode()));
        res.setCity(sysAreaEntity.getName());
        return res;
    }


    /**
     * 房东房源列表
     * @param req
     * @return
     */
    @Override
    public PageData<ResponseHouseListEntity> landlordHouseList(RequestHouseListEntity req) {
        Integer total = baseMapper.landlordHouseListTotal(req);
        if(total<=req.getLimit()){
            req.setSqlPage(0);
        }
        List<ResponseHouseListEntity> list= baseMapper.landlordHouseList(req);
        return PageData.ok(list,total);
    }

    /**
     * 房源表-新增
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveClientHouse(RequestHouseUpdateEntity req) {
        req.setTreeCode(sysAreaService.getTreeCodeByCityName(req.getCity()));
        req.setPoint("POINT("+req.getPointLng()+" "+req.getPointLat()+")");
        req.setStartDate(req.getStartDate()+" 14:00:00");
        req.setEndDate(req.getEndDate()+" 12:00:00");
        baseMapper.addHouse(req);
        Integer houseId= Integer.valueOf(req.getId());
        if(null == houseId){
            throw new CustomException("房源添加失败");
        }
        //增加房型
        List<ClientHouseResidenceEntity> residenceEntityList = new ArrayList<>();
        for(String residenceId : req.getResidenceList()){
            ClientHouseResidenceEntity entity = new ClientHouseResidenceEntity();
            entity.setHouseId(Long.valueOf(houseId));
            entity.setResidenceId(Long.valueOf(residenceId));
            residenceEntityList.add(entity);
        }
        if(!clientHouseResidenceService.saveBatch(residenceEntityList)){
            throw new CustomException("房源添加失败");
        }
        //增加配套
        List<ClientHouseMatchEntity> matchEntityList = new ArrayList<>();
        for(String matchId : req.getMatchList()){
            ClientHouseMatchEntity entity = new ClientHouseMatchEntity();
            entity.setHouseId(Long.valueOf(houseId));
            entity.setMatchId(Long.valueOf(matchId));
            matchEntityList.add(entity);
        }
        if(!clientHouseMatchService.saveBatch(matchEntityList)){
            throw new CustomException("房源添加失败");
        }
        //增加照片
        List<ClientHousePictureEntity> clientHousePictureEntities = new ArrayList<>();
        for(String img : req.getImages()){
            if(StringUtils.isNotBlank(img)){
                ClientHousePictureEntity entity = new ClientHousePictureEntity();
                entity.setHouseId(Long.valueOf(req.getId()));
                entity.setImgUrl(img);
                clientHousePictureEntities.add(entity);
            }
        }
        if(!clientHousePictureService.saveBatch(clientHousePictureEntities)){
            throw new CustomException("房源添加失败");
        }
        return 1;
    }


    /**
    * 房源表-删除
    * @param req
    * @return
    */
    @Override
    public int deleteClientHouseById(RequestHouseInfoEntity req) {
        ClientHouseEntity entity = new ClientHouseEntity();
        entity.setIsDel("1");
        entity.setId(Long.valueOf(req.getHouseId()));
        if(clientUserOrderService.getExistedOrder(req).size()>0){
            throw new CustomException("有正在进行中的订单，不能删除！");
        }
        return baseMapper.updateById(entity);
    }

    /**
    * 房源表-修改
    * @param req
    * @return
    */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateClientHouse(RequestHouseUpdateEntity req) {
        req.setTreeCode(sysAreaService.getTreeCodeByCityName(req.getCity()));
        req.setPoint("POINT("+req.getPointLng()+" "+req.getPointLat()+")");
        req.setStartDate(req.getStartDate()+" 14:00:00");
        req.setEndDate(req.getEndDate()+" 12:00:00");
        if(baseMapper.updateHouse(req)<=0){
            throw new CustomException("房源修改失败");
        }
        //删除房型
        clientHouseResidenceService.remove(new QueryWrapper<ClientHouseResidenceEntity>().eq("house_id",req.getId()));
        //增加房型
        List<ClientHouseResidenceEntity> residenceEntityList = new ArrayList<>();
        for(String residenceId : req.getResidenceList()){
            ClientHouseResidenceEntity entity = new ClientHouseResidenceEntity();
            entity.setHouseId(Long.valueOf(req.getId()));
            entity.setResidenceId(Long.valueOf(residenceId));
            residenceEntityList.add(entity);
        }
        if(!clientHouseResidenceService.saveBatch(residenceEntityList)){
            throw new CustomException("房源修改失败");
        }
        //删除配套
        clientHouseMatchService.remove(new QueryWrapper<ClientHouseMatchEntity>().eq("house_id",req.getId()));
        //增加配套
        List<ClientHouseMatchEntity> matchEntityList = new ArrayList<>();
        for(String matchId : req.getMatchList()){
            ClientHouseMatchEntity entity = new ClientHouseMatchEntity();
            entity.setHouseId(Long.valueOf(req.getId()));
            entity.setMatchId(Long.valueOf(matchId));
            matchEntityList.add(entity);
        }
        if(!clientHouseMatchService.saveBatch(matchEntityList)){
            throw new CustomException("房源修改失败");
        }
        //删除照片
        clientHousePictureService.remove(new QueryWrapper<ClientHousePictureEntity>().eq("house_id",req.getId()));
        //增加照片
        List<ClientHousePictureEntity> clientHousePictureEntities = new ArrayList<>();
        for(String img : req.getImages()){
            if(StringUtils.isNotBlank(img)){
                ClientHousePictureEntity entity = new ClientHousePictureEntity();
                entity.setHouseId(Long.valueOf(req.getId()));
                entity.setImgUrl(img);
                clientHousePictureEntities.add(entity);
            }
        }
        if(!clientHousePictureService.saveBatch(clientHousePictureEntities)){
            throw new CustomException("房源修改失败");
        }
        return 1;
    }

    /**
     * 查询房源详细
     * @param houseId
     * @return
     */
    @Override
    public ClientHouseEntity getHouseById(String houseId) {
        return baseMapper.getHouseById(houseId);
    }
}
