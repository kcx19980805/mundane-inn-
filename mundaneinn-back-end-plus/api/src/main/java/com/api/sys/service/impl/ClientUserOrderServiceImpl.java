package com.api.sys.service.impl;

import com.alipay.api.AlipayApiException;
import com.api.config.alipay.AliPayConfig;
import com.api.sys.entity.*;
import com.api.sys.requestEntity.*;
import com.api.sys.responseEntity.*;
import com.api.sys.service.*;
import com.common.constant.Constants;
import com.common.exception.CustomException;
import com.common.utils.date.DateUtils;
import com.common.utils.page.PageData;
import com.common.utils.pay.AliOrderEntity;
import com.common.utils.pay.Alipay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.api.sys.dao.ClientUserOrderDao;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@EnableScheduling
@Service("clientUserOrderService")
public class ClientUserOrderServiceImpl extends ServiceImpl<ClientUserOrderDao, ClientUserOrderEntity> implements ClientUserOrderService {
    @Autowired
    private ClientHouseService clientHouseService;
    @Autowired
    private AliPayConfig aliPayConfig;
    @Autowired
    private SysNoticeService sysNoticeService;
    @Autowired
    private ClientUserService clientUserService;
    @Autowired
    private ClientUserAmountRecordService clientUserAmountRecordService;

    /**
     * 每分钟检查一次订单是否超时，超时取消订单
     */
    @Scheduled(cron = "0 1-59 * * * ? ")
    public void cancelTimeOutOrder(){
        Integer minutes = 20;
        Integer res = baseMapper.cancelTimeOutOrder(minutes);
        log.info("取消订单数量:"+res);
    }

    /**
     * 每天0点检查一次订单是否到期超过一天，自动完成订单
     */
    //@Scheduled(cron = "0 0 0 1-31 * ? ")
    @Scheduled(cron = "0 1-59 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void autoCompleteOrder(){
        List<ClientUserOrderEntity> orderIds = baseMapper.completeOrderId();
        List<ClientUserEntity> userEntityList = new ArrayList<>();
        List<ClientUserAmountRecordEntity> userAmountRecordEntityList = new ArrayList<>();
        for(ClientUserOrderEntity orderEntity : orderIds){
            //增加房东余额
            Long userId = baseMapper.getUserIdByOrderId(orderEntity.getId());
            ClientUserEntity clientUserEntity = clientUserService.getById(userId);
            ClientUserEntity updateUserEntity = new ClientUserEntity();
            updateUserEntity.setId(userId);
            updateUserEntity.setBalance(clientUserEntity.getBalance().add(orderEntity.getAmountActual()));
            userEntityList.add(updateUserEntity);
            //生成支付记录
            ClientUserAmountRecordEntity userAmountRecordEntity = new ClientUserAmountRecordEntity();
            userAmountRecordEntity.setOrderId(orderEntity.getId());
            userAmountRecordEntity.setUserId(userId);
            userAmountRecordEntity.setOperator("+");
            userAmountRecordEntity.setAmount(orderEntity.getAmountActual());
            userAmountRecordEntity.setReson("用户下单");
            userAmountRecordEntityList.add(userAmountRecordEntity);
        }
        //修改订单状态
        if(orderIds.size()>0){
            ClientUserOrderEntity clientUserOrderEntity = new ClientUserOrderEntity();
            clientUserOrderEntity.setState(2);
            int count=baseMapper.update(clientUserOrderEntity,new QueryWrapper<ClientUserOrderEntity>().in("id",
                    orderIds.stream().map(item->item.getId()).collect(Collectors.toList())));
            if(count != orderIds.size()){
                throw new CustomException("订单批量完成异常");
            }
        }
        boolean userRes=true;
        if(userEntityList!=null && userEntityList.size()>0){
            userRes=clientUserService.updateBatchById(userEntityList);
        }
        boolean recordRes=true;
        if(userEntityList!=null && userEntityList.size()>0){
            recordRes=clientUserAmountRecordService.saveBatch(userAmountRecordEntityList);
        }
        if(!userRes || !recordRes){
            System.err.println(userRes);
            System.err.println(recordRes);
            throw new CustomException("批量增加用户余额异常");
        }
        log.info("已完成订单数量:"+orderIds.size());
    }

    /**
     * 每天0点检查一次订单是否取消且不确定是否付款，自动退款
     */
    //@Scheduled(cron = "0 0 0 1-31 * ? ")
    @Scheduled(cron = "0 1-59 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void  autoCancelOrder(){
        //查询已取消的订单，批量退款
        List<ClientUserOrderEntity> cancelOrders=baseMapper.cancelOrderId();
        for (ClientUserOrderEntity cancelOrder :cancelOrders){
            cancelAliOrder(cancelOrder.getOrderNumber());
        }
        ClientUserOrderEntity clientUserOrderEntity = new ClientUserOrderEntity();
        clientUserOrderEntity.setState(4);
        int cancelCount=baseMapper.update(clientUserOrderEntity,new QueryWrapper<ClientUserOrderEntity>().eq("state",3).ne("payment_status",'0'));
        if(cancelCount != cancelOrders.size()){
            throw new CustomException("订单批量退款异常");
        }
        log.info("已退款订单数量:"+cancelCount);
    }

    /**
     * 用户订单-列表
     * @param req
     * @return
     */
    @Override
    public PageData<ResponseOrderListEntity> clientUserOrderList(RequestOrderListEntity req) {
        int total=baseMapper.clientUserOrderListTotal(req);
        if(total<=req.getLimit()){
            req.setSqlPage(0);
        }
        List<ResponseOrderListEntity> list= baseMapper.clientUserOrderList(req);
        return PageData.ok(list,total);
    }


    /**
     * 房东订单-列表
     * @param req
     * @return
     */
    @Override
    public PageData<ResponseOrderListEntity> landlordOrderList(RequestOrderListEntity req) {
        int total=baseMapper.landlordOrderListTotal(req);
        if(total<=req.getLimit()){
            req.setSqlPage(0);
        }
        List<ResponseOrderListEntity> list= baseMapper.landlordOrderList(req);
        return PageData.ok(list,total);
    }

    /**
     * 用户订单表-新增
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseOrderAddEntity saveClientUserOrder(RequestOrderAddEntity req) {
        String startDate=req.getStartDate()+" 14:00:00";
        String endDate=req.getEndDate()+" 12:00:00";
        if(baseMapper.chooseDateRangeIsExistOrder(startDate,endDate,req.getHouseId())>0){
            throw new CustomException("订单所选日期已有订单，请重新选择！");
        }
        RequestHouseInfoEntity requestHouseInfoEntity = new RequestHouseInfoEntity();
        requestHouseInfoEntity.setHouseId(req.getHouseId());
        ResponseHouseInfoEntity clientHouseEntity = clientHouseService.clientHouseInfo(requestHouseInfoEntity);
        if(null == clientHouseEntity){
            throw new CustomException("找不到房源！");
        }
        AliOrderEntity order=new AliOrderEntity();
        order.setOut_trade_no(req.getUserId()+"-"+System.currentTimeMillis());
        order.setUserId(req.getUserId());
        order.setSubject(clientHouseEntity.getName());
        order.setTotal_amount(req.getTotal_amount());
        ClientUserOrderEntity clientUserOrderEntity = new ClientUserOrderEntity();
        clientUserOrderEntity.setHouseId(Long.valueOf(req.getHouseId()));
        clientUserOrderEntity.setName(clientHouseEntity.getName());
        clientUserOrderEntity.setLocation(clientHouseEntity.getLocation());
        clientUserOrderEntity.setUserId(Long.valueOf(req.getUserId()));
        clientUserOrderEntity.setStartDate(DateUtils.stringToLocalDateTime(startDate,""));
        clientUserOrderEntity.setEndDate(DateUtils.stringToLocalDateTime(endDate,""));
        clientUserOrderEntity.setHousePrice(new BigDecimal(clientHouseEntity.getHousePrice()));
        clientUserOrderEntity.setAmountActual(new BigDecimal(req.getTotal_amount()));
        clientUserOrderEntity.setOrderNumber(order.getOut_trade_no());
        int res = baseMapper.insert(clientUserOrderEntity);
        if(res<=0){
            throw new CustomException("生成订单失败！");
        }
        ResponseOrderAddEntity responseOrderAddEntity = new ResponseOrderAddEntity();
        responseOrderAddEntity.setOut_trade_no(order.getOut_trade_no());
        responseOrderAddEntity.setQrCode(getAliQrCode(order));
        return responseOrderAddEntity;
    }

    /**
     * 重新支付订单，下单时没有支付
     * @param req
     * @return
     */
    @Override
    public ResponseOrderAddEntity payOrder(RequestOrderInfoEntity req) {
        ClientUserOrderEntity clientUserOrderEntity = baseMapper.selectById(req.getOrderId());
        if(null == clientUserOrderEntity){
            throw new CustomException("订单不存在！");
        }
        if(clientUserOrderEntity.getState()!=0){
            throw new CustomException("订单异常！");
        }
        long diff = (long) DateUtils.minusToLocalDateTime(clientUserOrderEntity.getStartDate(),LocalDateTime.now()).get("minutes");
        //过20分钟超时
        if(diff>20){
            throw new CustomException("订单已超时！");
        }
        AliOrderEntity order=new AliOrderEntity();
        order.setOut_trade_no(clientUserOrderEntity.getOrderNumber());
        order.setUserId(String.valueOf(clientUserOrderEntity.getUserId()));
        order.setSubject(clientUserOrderEntity.getName());
        order.setTotal_amount(String.valueOf(clientUserOrderEntity.getAmountActual()));
        ResponseOrderAddEntity responseOrderAddEntity = new ResponseOrderAddEntity();
        responseOrderAddEntity.setOut_trade_no(order.getOut_trade_no());
        responseOrderAddEntity.setQrCode(getAliQrCode(order));
        return responseOrderAddEntity;
    }

    /**
     * 若没有回调，手动查询支付结果
     * @param req
     * @return
     */
    @Override
    public boolean getOrderResult(RequestOrderResultEntity req) {
        boolean res;
        try {
            res = Alipay.resultQuery(req.getOut_trade_no(),"",aliPayConfig.getGatewayUrl(),aliPayConfig.getAppId(),aliPayConfig.getPrivateKey(),
                    aliPayConfig.getCharset(),aliPayConfig.getPublicKey(),aliPayConfig.getSignType(),
                    aliPayConfig.getReturnUrl(),aliPayConfig.getNotifyUrl());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            cancelAliOrder(req.getOut_trade_no());
            return false;
        }
        return res;
    }


    /**
     * 支付-修改订单状态
     * @param out_trade_no 支付宝订单号
     * @param status 支付宝支付状态(0支付失败 1支付成功)
     * @return
     */
    @Override
    public int updateClientUserOrder(String out_trade_no,String status) {
        ClientUserOrderEntity clientUserOrderEntity = new ClientUserOrderEntity();
        clientUserOrderEntity.setPaymentStatus(status);
        clientUserOrderEntity.setPayTime(LocalDateTime.now());
        //支付宝支付成功，订单成功
        if("1".equals(status)){
            clientUserOrderEntity.setState(1);
        }
        return baseMapper.update(clientUserOrderEntity,new QueryWrapper<ClientUserOrderEntity>().eq("order_number",out_trade_no));
    }

    /**
     * 用户取消订单
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int userCancelOrder(RequestOrderInfoEntity req) {
        ClientUserOrderEntity clientUserOrderEntity = baseMapper.selectById(req.getOrderId());
        RequestHouseInfoEntity requestHouseInfoEntity = new RequestHouseInfoEntity();
        requestHouseInfoEntity.setHouseId(String.valueOf(clientUserOrderEntity.getHouseId()));
        ResponseHouseInfoEntity clientHouseEntity = clientHouseService.clientHouseInfo(requestHouseInfoEntity);
        if(null == clientHouseEntity){
            throw new CustomException("找不到房源！");
        }
        //发送通知给房东
        SysNoticeEntity sysNoticeEntity = new SysNoticeEntity();
        sysNoticeEntity.setSendType("0");
        sysNoticeEntity.setUserId(Long.valueOf(clientHouseEntity.getUserId()));
        sysNoticeEntity.setNoticeType("0");
        sysNoticeEntity.setContent("用户取消了\""+clientUserOrderEntity.getName()+"\"的订单");
        sysNoticeService.save(sysNoticeEntity);
        return refundAliOrder(clientUserOrderEntity);
    }

    /**
     * 房东取消订单
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int landlordCancelOrder(RequestOrderInfoEntity req) {
        ClientUserOrderEntity clientUserOrderEntity = baseMapper.selectById(req.getOrderId());
        //发送通知给用户
        SysNoticeEntity sysNoticeEntity = new SysNoticeEntity();
        sysNoticeEntity.setSendType("0");
        sysNoticeEntity.setUserId(clientUserOrderEntity.getUserId());
        sysNoticeEntity.setNoticeType("0");
        sysNoticeEntity.setContent("房东取消了\""+clientUserOrderEntity.getName()+"\"的订单");
        sysNoticeService.save(sysNoticeEntity);
        return refundAliOrder(clientUserOrderEntity);
    }

    /**
     * 用户删除订单
     * @param req
     * @return
     */
    @Override
    public int userDeleteOrder(RequestOrderInfoEntity req) {
        ClientUserOrderEntity clientUserOrderEntity = baseMapper.selectById(req.getOrderId());
        clientUserOrderEntity.setId(Long.valueOf(req.getOrderId()));
        clientUserOrderEntity.setIsDelUser("1");
        return baseMapper.updateById(clientUserOrderEntity);
    }

    /**
     * 房东删除订单
     * @param req
     * @return
     */
    @Override
    public int landlordDeleteOrder(RequestOrderInfoEntity req) {
        ClientUserOrderEntity clientUserOrderEntity = baseMapper.selectById(req.getOrderId());
        clientUserOrderEntity.setId(Long.valueOf(req.getOrderId()));
        clientUserOrderEntity.setIsDelLandlord("1");
        return baseMapper.updateById(clientUserOrderEntity);
    }

    /**
     * 查询房源正在进行中的订单
     * @param req
     * @return
     */
    @Override
    public List<ResponseUserOrderListEntity> getExistedOrder(RequestHouseInfoEntity req) {
        return baseMapper.getExistedOrder(req);
    }

    /**
     * 查询钱箱信息
     * @param userId
     * @return
     */
    @Override
    public ResponseOrderMoneyEntity getMoney(String userId) {
        ResponseOrderMoneyEntity res = new ResponseOrderMoneyEntity();
        String beenCredited = baseMapper.getMoneyByOrderState(userId,"2");
        String pending = baseMapper.getMoneyByOrderState(userId,"1");
        res.setBeenCredited(beenCredited==null?"0":beenCredited);
        res.setPending(pending==null?"0":pending);
        return res;
    }

    /*================================================支付宝公共方法===================================================*/
    /**
     * 生成支付宝二维码
     * @param order
     * @return
     */
    private String getAliQrCode(AliOrderEntity order) {
        String qrcode;
        try {
            qrcode = Alipay.payQrCode(order,aliPayConfig.getGatewayUrl(),aliPayConfig.getAppId(),aliPayConfig.getPrivateKey(),
                    aliPayConfig.getCharset(),aliPayConfig.getPublicKey(),aliPayConfig.getSignType(),
                    aliPayConfig.getReturnUrl(),aliPayConfig.getNotifyUrl());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new CustomException("生成二维码失败！");
        }
        return qrcode;
    }

    /**
     * 支付宝订单退款
     * @param clientUserOrderEntity
     * @return
     */
    public int refundAliOrder(ClientUserOrderEntity clientUserOrderEntity){
        if(null == clientUserOrderEntity){
            throw new CustomException("订单不存在！");
        }
        if(clientUserOrderEntity.getState()==3){
            throw new CustomException("订单已取消！");
        }
        long diff = (long) DateUtils.minusToLocalDateTime(clientUserOrderEntity.getStartDate(),LocalDateTime.now()).get("days");
        if(diff>=0){
            throw new CustomException("入住当天之后不能取消！");
        }
        //退款
        try {
            boolean res = Alipay.orderRefund(clientUserOrderEntity.getOrderNumber(),"", String.valueOf(clientUserOrderEntity.getAmountActual()),aliPayConfig.getGatewayUrl(),aliPayConfig.getAppId(),aliPayConfig.getPrivateKey(),
                    aliPayConfig.getCharset(),aliPayConfig.getPublicKey(),aliPayConfig.getSignType(),
                    aliPayConfig.getReturnUrl(),aliPayConfig.getNotifyUrl());
            if(!res){
                throw new CustomException("退款失败！");
            }else{
                clientUserOrderEntity.setState(4);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new CustomException("退款异常！");
        }
        return baseMapper.updateById(clientUserOrderEntity);
    }

    /**
     * 支付宝订单取消
     * @param out_trade_no
     */
    private void cancelAliOrder(String out_trade_no){
        int count=3;
        boolean isCancel;
        //查询支付结果异常，取消订单,重试三次
        while(count>0){
            count--;
            try {
                isCancel=Alipay.cancelOrder(out_trade_no,"",aliPayConfig.getGatewayUrl(),aliPayConfig.getAppId(),aliPayConfig.getPrivateKey(),
                        aliPayConfig.getCharset(),aliPayConfig.getPublicKey(),aliPayConfig.getSignType(),
                        aliPayConfig.getReturnUrl(),aliPayConfig.getNotifyUrl());
                if(isCancel){
                    break;
                }
                Thread.sleep(3000);
            } catch (Exception exception) {
            }
        }
    }
}
