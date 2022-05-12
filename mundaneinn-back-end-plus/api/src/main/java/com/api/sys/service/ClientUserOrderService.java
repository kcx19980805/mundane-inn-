package com.api.sys.service;

import com.alipay.api.AlipayApiException;
import com.api.sys.requestEntity.*;
import com.api.sys.responseEntity.ResponseOrderAddEntity;
import com.api.sys.responseEntity.ResponseOrderListEntity;
import com.api.sys.responseEntity.ResponseOrderMoneyEntity;
import com.api.sys.responseEntity.ResponseUserOrderListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.ClientUserOrderEntity;
import com.common.utils.page.PageData;
import com.common.utils.pay.AliOrderEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 用户订单表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface ClientUserOrderService extends IService<ClientUserOrderEntity> {

    /**
     * 用户订单-列表
     * @param req
     * @return
     */
    PageData<ResponseOrderListEntity> clientUserOrderList(RequestOrderListEntity req);

    /**
     * 房东订单-列表
     * @param req
     * @return
     */
    PageData<ResponseOrderListEntity> landlordOrderList(RequestOrderListEntity req);

    /**
    * 用户订单表-新增
    * @param req
    * @return
    */
    ResponseOrderAddEntity saveClientUserOrder(RequestOrderAddEntity req);

    /**
     * 重新支付订单，下单时没有支付
     * @param req
     * @return
     */
    ResponseOrderAddEntity payOrder(RequestOrderInfoEntity req);

    /**
     * 若没有回调，手动查询支付结果
     * @param req
     * @return
     */
    boolean getOrderResult(RequestOrderResultEntity req);

    /**
     * 支付-修改订单状态
     * @param out_trade_no 支付宝订单号
     * @param status 支付宝支付状态(0支付失败 1支付成功)
     * @return
     */
     int updateClientUserOrder(String out_trade_no,String status) ;

    /**
     * 用户取消订单
     * @param req
     * @return
     */
    int userCancelOrder(RequestOrderInfoEntity req);

    /**
     * 房东取消订单
     * @param req
     * @return
     */
    int landlordCancelOrder(RequestOrderInfoEntity req);

    /**
    * 用户删除订单
    * @param req
    * @return
    */
    int userDeleteOrder(RequestOrderInfoEntity req);

    /**
     * 房东删除订单
     * @param req
     * @return
     */
    int landlordDeleteOrder(RequestOrderInfoEntity req);

    /**
     * 查询房源正在进行中的订单
     * @param req
     * @return
     */
    List<ResponseUserOrderListEntity> getExistedOrder(RequestHouseInfoEntity req);

    /**
     * 查询钱箱信息
     * @param userId
     * @return
     */
    ResponseOrderMoneyEntity getMoney(String userId);

}

