package com.api.sys.service;

import com.api.sys.requestEntity.RequestHouseCommentAddEntity;
import com.api.sys.requestEntity.RequestHouseCommentListEntity;
import com.api.sys.responseEntity.ResponseHouseCommentListEntity;
import com.api.sys.responseEntity.ResponseHouseListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.ClientHouseCommentEntity;
import com.common.utils.page.PageData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 房源评论表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface ClientHouseCommentService extends IService<ClientHouseCommentEntity> {
    /**
    * 房源评论表-列表
    * @return
    */
    PageData<ResponseHouseCommentListEntity> clientHouseCommentList(RequestHouseCommentListEntity req);

    /**
    * 房源评论表-新增
    * @param req
    * @return
    */
    int saveClientHouseComment(HttpServletRequest request, RequestHouseCommentAddEntity req);

}

