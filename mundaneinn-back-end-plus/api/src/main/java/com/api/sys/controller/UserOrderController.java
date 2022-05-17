package com.api.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.api.config.alipay.AliPayConfig;
import com.api.config.websocket.WebSocketHandlerPay;
import com.api.sys.requestEntity.*;
import com.api.sys.service.ClientHouseService;
import com.api.sys.service.ClientUserOrderService;
import com.common.constant.Constants;
import com.common.utils.Result;
import com.common.utils.pay.AliOrderEntity;
import com.common.utils.pay.AliReturnPayEntity;
import com.common.utils.pay.Alipay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@RestController
@Api(tags = "订单")
@RequestMapping("/api")
@Slf4j
public class UserOrderController {
    @Autowired
    private AliPayConfig aliPayConfig;
    @Autowired
    private ClientUserOrderService clientUserOrderService;
    @Autowired
    private WebSocketHandlerPay webSocketHandlerPay;

    @GetMapping("/getExistedOrder")
    @ApiOperation(value = "查询房源正在进行中的订单")
    public Result getExistedOrder(RequestHouseInfoEntity req) {
        return Result.ok(clientUserOrderService.getExistedOrder(req));
    }

/*    @PostMapping(value = "/order/alipayHtml")
    @ApiOperation(value = "支付宝支付api,网页支付")
    public void alipayHtml(HttpServletResponse response,@Validated @RequestBody  AliOrderEntity order) throws AlipayApiException {
        order.setOut_trade_no(UUID.randomUUID().toString());
        response.setContentType("text/html;charset=" + aliPayConfig.getCharset());
        try {
            String html=Alipay.payHtml(order,aliPayConfig.getGatewayUrl(),aliPayConfig.getAppId(),aliPayConfig.getPrivateKey(),
                    aliPayConfig.getCharset(),aliPayConfig.getPublicKey(),aliPayConfig.getSignType(),
                    aliPayConfig.getReturnUrl(),aliPayConfig.getNotifyUrl());
            //直接将完整的表单html输出到页面
            response.getWriter().write(html);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @PostMapping("/createAliQR")
    @ApiOperation(value = "支付宝支付api,二维码支付")
    public Result createAliQR(@Validated @RequestBody RequestOrderAddEntity req){
        return Result.ok(clientUserOrderService.saveClientUserOrder(req));
    }

    /**
     * 支付宝回调函数
     * @param request
     * @param response
     * @param returnPay
     * @throws IOException
     */
    @RequestMapping("/alipayCallBack")
    public void alipayCallBack(HttpServletRequest request, HttpServletResponse response, AliReturnPayEntity returnPay) throws IOException {
        //获取用户id
        log.info("支付宝的的回调函数被调用");
        if (!Alipay.checkSign(request,aliPayConfig.getPublicKey(),aliPayConfig.getCharset(),aliPayConfig.getSignType())) {
            log.error("验签失败");
            return;
        }
        if (null == returnPay) {
            log.warn("支付宝的returnPay返回为空");
            return;
        }
        JSONObject body = JSONObject.parseObject(returnPay.getBody());
        String userId = body.getString("userId");
        if (returnPay.getTrade_status().equals("TRADE_SUCCESS")) {
            log.info("支付宝支付成功");
            //修改订单状态
            clientUserOrderService.updateClientUserOrder(returnPay.getOut_trade_no(),"1");
        }else {
            log.info("支付宝支付失败");
            //修改订单状态
            clientUserOrderService.updateClientUserOrder(returnPay.getOut_trade_no(),"0");
        }
        //通知用户支付状态
        webSocketHandlerPay.sendMessageToUser(userId,new TextMessage(returnPay.getTrade_status()));
    }

    @GetMapping("/getAliOrderResult")
    @ApiOperation(value = "若没有回调，手动查询支付结果")
    public void getAliOrderResult(HttpServletRequest request,@Validated RequestOrderResultEntity req){
        boolean res = clientUserOrderService.getOrderResult(req);
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        if(res){
            //修改订单状态
            clientUserOrderService.updateClientUserOrder(req.getOut_trade_no(),"1");
            //通知用户支付状态
            webSocketHandlerPay.sendMessageToUser(userId,new TextMessage("TRADE_SUCCESS"));
        }
        else{
            //修改订单状态
            clientUserOrderService.updateClientUserOrder(req.getOut_trade_no(),"0");
            //通知用户支付状态
            webSocketHandlerPay.sendMessageToUser(userId,new TextMessage("TRADE_ERROR"));
        }
    }

    @GetMapping("/clientUserOrderList")
    @ApiOperation(value = "用户订单-列表")
    public Result clientUserOrderList(HttpServletRequest request, @Validated RequestOrderListEntity req){
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        req.setUserId(userId);
        return Result.ok(clientUserOrderService.clientUserOrderList(req));
    }

    @PostMapping("/payAliOrder")
    @ApiOperation(value = "用户订单-支付,二维码支付")
    public Result payAliOrder(@Validated @RequestBody RequestOrderInfoEntity req){
        return Result.ok(clientUserOrderService.payOrder(req));
    }

    @PostMapping("/userCancelOrder")
    @ApiOperation(value = "用户取消订单")
    public Result userCancelOrder(@Validated @RequestBody RequestOrderInfoEntity req){
        return Result.toRow(clientUserOrderService.userCancelOrder(req));
    }

    @PostMapping("/userDeleteOrder")
    @ApiOperation(value = "用户删除订单")
    public Result userDeleteOrder(@Validated @RequestBody RequestOrderInfoEntity req){
        return Result.toRow(clientUserOrderService.userDeleteOrder(req));
    }

    @GetMapping("/landlordOrderList")
    @ApiOperation(value = "房东订单-列表")
    public Result landlordOrderList(HttpServletRequest request, @Validated RequestOrderListEntity req){
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        req.setUserId(userId);
        return Result.ok(clientUserOrderService.landlordOrderList(req));
    }

    @PostMapping("/landlordCancelOrder")
    @ApiOperation(value = "房东取消订单")
    public Result landlordCancelOrder(@Validated @RequestBody RequestOrderInfoEntity req){
        return Result.toRow(clientUserOrderService.landlordCancelOrder(req));
    }

    @PostMapping("/landlordDeleteOrder")
    @ApiOperation(value = "房东删除订单")
    public Result landlordDeleteOrder(@Validated @RequestBody RequestOrderInfoEntity req){
        return Result.toRow(clientUserOrderService.landlordDeleteOrder(req));
    }

    @GetMapping("/getMoney")
    @ApiOperation(value = "查询钱箱信息")
    public Result getMoney(HttpServletRequest request){
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        return Result.ok(clientUserOrderService.getMoney(userId));
    }
}
