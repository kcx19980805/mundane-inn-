package com.api.sys.service.impl;

import com.api.sys.entity.ClientHouseEntity;
import com.api.sys.entity.ClientUserEntity;
import com.api.sys.requestEntity.RequestHouseCommentAddEntity;
import com.api.sys.requestEntity.RequestHouseCommentListEntity;
import com.api.sys.requestEntity.RequestHouseInfoEntity;
import com.api.sys.responseEntity.ResponseHouseCommentListEntity;
import com.api.sys.responseEntity.ResponseHouseInfoEntity;
import com.api.sys.responseEntity.ResponseHouseListEntity;
import com.api.sys.service.ClientHouseService;
import com.api.sys.service.ClientUserService;
import com.common.constant.Constants;
import com.common.exception.CustomException;
import com.common.utils.page.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import com.api.sys.dao.ClientHouseCommentDao;
import com.api.sys.entity.ClientHouseCommentEntity;
import com.api.sys.service.ClientHouseCommentService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service("clientHouseCommentService")
public class ClientHouseCommentServiceImpl extends ServiceImpl<ClientHouseCommentDao, ClientHouseCommentEntity> implements ClientHouseCommentService {
    @Autowired
    private ClientHouseService clientHouseService;
    @Autowired
    private ClientUserService clientUserService;
    /**
    * 房源评论表-列表
    * @return
    */
    @Override
    public PageData<ResponseHouseCommentListEntity> clientHouseCommentList(RequestHouseCommentListEntity req){
        Integer total = baseMapper.clientHouseCommentListTotal(req);
        if(total<=req.getLimit()){
            req.setSqlPage(0);
        }
        List<ResponseHouseCommentListEntity> list= baseMapper.clientHouseCommentList(req);
        return PageData.ok(list,total);
    }

    /**
     * 房源评论表-新增
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveClientHouseComment(HttpServletRequest request, RequestHouseCommentAddEntity req) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        ClientHouseCommentEntity clientHouseCommentEntity = new ClientHouseCommentEntity();
        clientHouseCommentEntity.setUserId(Long.valueOf(userId));
        clientHouseCommentEntity.setLandlordId(baseMapper.getUserIdByOrderId(req.getOrderId()));
        clientHouseCommentEntity.setOrderId(Long.valueOf(req.getOrderId()));
        clientHouseCommentEntity.setHouseId(Long.valueOf(req.getHouseId()));
        clientHouseCommentEntity.setContent(req.getContent());
        clientHouseCommentEntity.setScore(new BigDecimal(req.getScore()));
        if(baseMapper.selectCount(new QueryWrapper<ClientHouseCommentEntity>().eq("order_id",req.getOrderId()).eq("user_id",userId))>0){
            throw new CustomException("您已评论，不能重复评论");
        }
        //更新房源评分
        ClientHouseEntity houseEntity = clientHouseService.getHouseById(req.getHouseId());
        ClientHouseEntity updateHouseEntity = new ClientHouseEntity();
        int houseCount = houseEntity.getCommentsNumber();
        BigDecimal houseScore = houseEntity.getScore();
        houseScore = houseScore.multiply(new BigDecimal(houseCount)).add(new BigDecimal(req.getScore())).divide(new BigDecimal(houseCount+1));
        updateHouseEntity.setCommentsNumber(houseCount+1);
        updateHouseEntity.setScore(houseScore);
        updateHouseEntity.setId(houseEntity.getId());
        clientHouseService.updateById(updateHouseEntity);
        //更新房东评分
        ClientUserEntity clientUserEntity = clientUserService.getById(clientHouseCommentEntity.getLandlordId());
        int userCount = clientUserEntity.getCommentsNumber();
        BigDecimal userScore = clientUserEntity.getScore();
        userScore = userScore.multiply(new BigDecimal(userCount)).add(new BigDecimal(req.getScore())).divide(new BigDecimal(userCount+1));
        clientUserEntity.setCommentsNumber(userCount+1);
        clientUserEntity.setScore(userScore);
        clientUserService.updateById(clientUserEntity);
        return baseMapper.insert(clientHouseCommentEntity);
    }

}
