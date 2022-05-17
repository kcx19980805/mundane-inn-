package com.common.utils.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.common.exception.CustomException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 调用支付宝支付的组件
 */
public class Alipay {

    /**
     * 支付接口,返回支付表单，前端调用如下
     * const divForm = document.getElementsByTagName("div");
     * if (divForm.length) {
     *  document.body.removeChild(divForm[0]);
     * }
     * const div = document.createElement("div");
     * div.innerHTML = res; // res就是接口返回的form 表单字符串
     * document.body.appendChild(div);
     * document.forms[0].setAttribute("target", "_self");
     * document.forms[0].submit();
     * @param order 订单
     * @param gatewayUrl 支付宝网关
     * @param _appId 公司支付宝APPID
     * @param _privateKey 商户私钥
     * @param _charset 字符编码格式
     * @param publicKey 支付宝公钥
     * @param _signType 签名方式
     * @param _returnUrl 页面跳转同步通知页面路径
     * @param _notifyUrl 服务器异步通知页面路径
     * @return
     * @throws AlipayApiException
     */
    public static String payHtml(AliOrderEntity order,String gatewayUrl,String _appId,String _privateKey,String _charset,
                      String publicKey,String _signType,String _returnUrl,String _notifyUrl) throws AlipayApiException {
        // 支付宝网关
        String serverUrl = gatewayUrl;
        // APPID
        String appId = _appId;
        // 商户私钥, 即PKCS8格式RSA2私钥
        String privateKey = _privateKey;
        // 格式化为 json 格式
        String format = "json";
        // 字符编码格式
        String charset = _charset;
        // 支付宝公钥, 即对应APPID下的支付宝公钥
        String alipayPublicKey = publicKey;
        // 签名方式
        String signType = _signType;
        // 页面跳转同步通知页面路径
        String returnUrl = _returnUrl;
        // 服务器异步通知页面路径
        String notifyUrl = _notifyUrl;
        // 1、获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(returnUrl);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(notifyUrl);
        // 封装参数(以json格式封装)
        alipayRequest.setBizContent(JSON.toJSONString(order));
        // 3、请求支付宝进行付款，并获取支付结果
        String html = alipayClient.pageExecute(alipayRequest).getBody();
        // 返回付款信息
        return html;
    }

    /**
     * 支付接口,返回支付二维码，需要前端或后端把字符串转换为二维码图片
     * @param order 订单
     * @param gatewayUrl 支付宝网关
     * @param _appId 公司支付宝APPID
     * @param _privateKey 商户私钥
     * @param _charset 字符编码格式
     * @param publicKey 支付宝公钥
     * @param _signType 签名方式
     * @param _returnUrl 页面跳转同步通知页面路径
     * @param _notifyUrl 服务器异步通知页面路径
     * @return
     */
    public static String payQrCode(AliOrderEntity order,String gatewayUrl,String _appId,String _privateKey,String _charset,
                                   String publicKey,String _signType,String _returnUrl,String _notifyUrl) throws AlipayApiException {
        // 支付宝网关
        String serverUrl = gatewayUrl;
        // APPID
        String appId = _appId;
        // 商户私钥, 即PKCS8格式RSA2私钥
        String privateKey = _privateKey;
        // 格式化为 json 格式
        String format = "json";
        // 字符编码格式
        String charset = _charset;
        // 支付宝公钥, 即对应APPID下的支付宝公钥
        String alipayPublicKey = publicKey;
        // 签名方式
        String signType = _signType;
        // 页面跳转同步通知页面路径
        String returnUrl = _returnUrl;
        // 服务器异步通知页面路径
        String notifyUrl = _notifyUrl;
        // 1、获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        // 2、设置请求参数
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(notifyUrl);
        //同步回调地址,即支付成功后前端页面跳转的地址，这里不需要
        //request.setReturnUrl("");
        //商品明细信息，按需传入
        JSONObject json = new JSONObject();
        json.put("userId", order.getUserId());
        //支付参数
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        //订单编号
        model.setOutTradeNo(order.getOut_trade_no());
        //订单标题
        model.setSubject(order.getSubject());
        //订单金额，精确到小数点后两位
        model.setTotalAmount(order.getTotal_amount());
        //订单描述
        model.setBody(json.toString());
        //超时时间参数
        model.setTimeoutExpress(order.getTimeout_express());
        //产品编号
        model.setStoreId(order.getProduct_code());
        request.setBizModel(model);
        //3.通过alipayClient调用API，获得对应的response类
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            return response.getQrCode();
        } else {
            throw new CustomException("生成支付二维码失败");
        }
    }


    /**
     * 支付宝的交易结果查询接口，若支付宝没有回调，手动查询结果
     * 通过商户网站唯一订单号 out_trade_no 或支付宝交易号 trade_no 查询对应订单支付情况。
     * @param out_trade_no 支付时传入的商户订单号 与trade_no至少存在一个
     * @param trade_no 支付时返回的支付宝交易号 与out_trade_no至少存在一个
     * @param gatewayUrl 支付宝网关
     * @param _appId 公司支付宝APPID
     * @param _privateKey 商户私钥
     * @param _charset 字符编码格式
     * @param publicKey 支付宝公钥
     * @param _signType 签名方式
     * @param _returnUrl 页面跳转同步通知页面路径
     * @param _notifyUrl 服务器异步通知页面路径
     * @return
     */
    public static boolean resultQuery(String out_trade_no,String trade_no,String gatewayUrl,String _appId,String _privateKey,String _charset,
                                      String publicKey,String _signType,String _returnUrl,String _notifyUrl) throws AlipayApiException {
        // 支付宝网关
        String serverUrl = gatewayUrl;
        // APPID
        String appId = _appId;
        // 商户私钥, 即PKCS8格式RSA2私钥
        String privateKey = _privateKey;
        // 格式化为 json 格式
        String format = "json";
        // 字符编码格式
        String charset = _charset;
        // 支付宝公钥, 即对应APPID下的支付宝公钥
        String alipayPublicKey = publicKey;
        // 签名方式
        String signType = _signType;
        // 页面跳转同步通知页面路径
        String returnUrl = _returnUrl;
        // 服务器异步通知页面路径
        String notifyUrl = _notifyUrl;
        // 1、获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        //2.设置业务参数
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model= new AlipayTradeQueryModel();
        model.setOutTradeNo(out_trade_no);
        model.setTradeNo(trade_no);
        request.setBizModel(model);
        //3.通过alipayClient调用API，获得对应的response类
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            return true;
        } else {
            return false;
        }
    }


    /**
     * 支付宝的退款接口
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，
     * 支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
     * 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款。
     * @param out_trade_no 支付时传入的商户订单号 与trade_no至少存在一个
     * @param trade_no 支付时返回的支付宝交易号 与out_trade_no至少存在一个
     * @param totalAmount  需要退款的金额，该金额不能大于订单金额,单位为元
     * @param gatewayUrl 支付宝网关
     * @param _appId 公司支付宝APPID
     * @param _privateKey 商户私钥
     * @param _charset 字符编码格式
     * @param publicKey 支付宝公钥
     * @param _signType 签名方式
     * @param _returnUrl 页面跳转同步通知页面路径
     * @param _notifyUrl 服务器异步通知页面路径
     * @return
     */
    public static boolean orderRefund(String out_trade_no,String trade_no,String totalAmount,String gatewayUrl,String _appId,String _privateKey,String _charset,
                                  String publicKey,String _signType,String _returnUrl,String _notifyUrl) throws AlipayApiException {
        // 支付宝网关
        String serverUrl = gatewayUrl;
        // APPID
        String appId = _appId;
        // 商户私钥, 即PKCS8格式RSA2私钥
        String privateKey = _privateKey;
        // 格式化为 json 格式
        String format = "json";
        // 字符编码格式
        String charset = _charset;
        // 支付宝公钥, 即对应APPID下的支付宝公钥
        String alipayPublicKey = publicKey;
        // 签名方式
        String signType = _signType;
        // 页面跳转同步通知页面路径
        String returnUrl = _returnUrl;
        // 服务器异步通知页面路径
        String notifyUrl = _notifyUrl;
        // 1、获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        //2.设置业务参数
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model= new AlipayTradeRefundModel();
        model.setOutTradeNo(out_trade_no);
        model.setTradeNo(trade_no);
        model.setRefundAmount(totalAmount);
        request.setBizModel(model);
        //3.通过alipayClient调用API，获得对应的response类
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取消订单接口,仅发生支付系统超时或者支付结果未知时可调用本接口撤销交易，其他正常支付的单如需实现相同功能请调用 alipay.trade.refund
     * 如果此订单用户支付失败，支付宝将关闭此订单。
     * 如果此订单用户支付成功，支付宝将退还订单资金给用户。
     * @param out_trade_no 支付时传入的商户订单号
     * @param trade_no 支付时返回的支付宝交易号
     * @param gatewayUrl 支付宝网关
     * @param _appId 公司支付宝APPID
     * @param _privateKey 商户私钥
     * @param _charset 字符编码格式
     * @param publicKey 支付宝公钥
     * @param _signType 签名方式
     * @param _returnUrl 页面跳转同步通知页面路径
     * @param _notifyUrl 服务器异步通知页面路径
     * @return  true成功 false失败，需要重新调用接口
     * @throws AlipayApiException
     */
    public static boolean cancelOrder(String out_trade_no,String trade_no,String gatewayUrl,String _appId,String _privateKey,String _charset,
                                     String publicKey,String _signType,String _returnUrl,String _notifyUrl) throws AlipayApiException {
        // 支付宝网关
        String serverUrl = gatewayUrl;
        // APPID
        String appId = _appId;
        // 商户私钥, 即PKCS8格式RSA2私钥
        String privateKey = _privateKey;
        // 格式化为 json 格式
        String format = "json";
        // 字符编码格式
        String charset = _charset;
        // 支付宝公钥, 即对应APPID下的支付宝公钥
        String alipayPublicKey = publicKey;
        // 签名方式
        String signType = _signType;
        // 页面跳转同步通知页面路径
        String returnUrl = _returnUrl;
        // 服务器异步通知页面路径
        String notifyUrl = _notifyUrl;
        // 1、获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        //2.设置业务参数
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        AlipayTradeCancelModel model = new AlipayTradeCancelModel();
        model.setOutTradeNo(out_trade_no);
        model.setTradeNo(trade_no);
        request.setBizModel(model);
        //3.通过alipayClient调用API，获得对应的response类
        AlipayTradeCancelResponse response = alipayClient.execute(request);
        System.out.print(response.getBody());
        JSONObject jsonObj = JSONObject.parseObject(response.getBody());
        //Y：撤销不成功，需要重新调用接口。N：撤销不成功，不需要重新调用接口。
        if("N".equals(jsonObj.get("retry_flag").toString())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 验签，是否正确
     * @param request 请求参数
     * @param publicKey 支付宝公钥
     * @param charset 字符编码格式
     * @param signType 签名方式
     * @return
     */
    public static boolean checkSign(HttpServletRequest request,String publicKey,String charset,String signType){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();
        requestMap.forEach((key, values) -> {
            String strs = "";
            for(String value : values) {
                strs = strs + value;
            }
            paramsMap.put(key, strs);
        });
        //调用SDK验证签名
        try {
            return  AlipaySignature.rsaCheckV1(paramsMap, publicKey, charset, signType);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }
    }
}
