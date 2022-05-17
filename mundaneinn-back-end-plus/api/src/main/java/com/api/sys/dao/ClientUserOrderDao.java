package com.api.sys.dao;

import com.api.sys.entity.ClientUserOrderEntity;
import com.api.sys.requestEntity.RequestHouseInfoEntity;
import com.api.sys.requestEntity.RequestOrderInfoEntity;
import com.api.sys.requestEntity.RequestOrderListEntity;
import com.api.sys.responseEntity.ResponseOrderListEntity;
import com.api.sys.responseEntity.ResponseUserOrderListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户订单表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientUserOrderDao extends BaseMapper<ClientUserOrderEntity> {
    /**
     * 用户订单-列表
     * @return
     */
    List<ResponseOrderListEntity> clientUserOrderList(RequestOrderListEntity req);

    /**
     * 用户订单-列表-总数
     * @return
     */
    Integer clientUserOrderListTotal(RequestOrderListEntity req);

    /**
     * 房东订单-列表
     * @return
     */
    List<ResponseOrderListEntity> landlordOrderList(RequestOrderListEntity req);

    /**
     * 房东订单-列表-总数
     * @param req
     * @return
     */
    Integer landlordOrderListTotal(RequestOrderListEntity req);

    /**
     * 取消超时未支付的订单
     * @param minutes 分钟
     * @return
     */
    Integer cancelTimeOutOrder(Integer minutes);

    /**
     * 通过订单号查询订单信息
     * @param orderNumber
     * @return
     */
    ClientUserOrderEntity getUserOrderByOrderNumber(String orderNumber);

    /**
     * 查询房源正在进行中的订单
     * @param req
     * @return
     */
    List<ResponseUserOrderListEntity> getExistedOrder(RequestHouseInfoEntity req);

    /**
     * 查询所订日期范围内是否存在进行中订单
     * @return
     */
    Integer chooseDateRangeIsExistOrder(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("houseId") String houseId);

    /**
     * 查询已完成的订单
     * @return
     */
    List<ClientUserOrderEntity> completeOrderId();

    /**
     * 查询已取消的订单
     * @return
     */
    List<ClientUserOrderEntity> cancelOrderId();

    /**
     * 根据订单id查询房东id
     * @param orderId
     * @return
     */
    Long getUserIdByOrderId(Long orderId);

    /**
     * 查询本月房东不同账单的金额合计
     * @param userId
     * @param state
     * @return
     */
    String getMoneyByOrderState(@Param("userId") String userId,@Param("state") String state);
}
