import axios from "@/utils/axios";
export  default {
    //查询房源正在进行中的订单
    getExistedOrder(houseId){
        return axios({
            url: '/getExistedOrder',
            method: 'get',
            params: {houseId}
        })
    },
    //生成支付宝支付二维码
    getAliQrCode(userId,houseId,startDate,endDate,total_amount){
        return axios({
            url: '/createAliQR',
            method: 'post',
            data: {userId,houseId,startDate,endDate,total_amount}
        })
    },
    //用户订单列表
    clientUserOrderList(data){
        return axios({
            url: '/clientUserOrderList',
            method: 'get',
            params: data
        })
    },
    //用户订单-支付
    payAliOrder(orderId){
        return axios({
            url: '/payAliOrder',
            method: 'post',
            data: {orderId}
        })
    },
    //没有回调通知，手动获取支付结果
    getAliOrderResult(out_trade_no){
        return axios({
            url: '/getAliOrderResult',
            method: 'get',
            params: {out_trade_no}
        })
    },
    //用户取消订单
    userCancelOrder(orderId){
        return axios({
            url: '/userCancelOrder',
            method: 'post',
            data: {orderId}
        })
    },
    //用户删除订单
    userDeleteOrder(orderId){
        return axios({
            url: '/userDeleteOrder',
            method: 'post',
            data: {orderId}
        })
    },
    //房东订单列表
    landlordOrderList(data){
        return axios({
            url: '/landlordOrderList',
            method: 'get',
            params: data
        })
    },
    //房东取消订单
    landlordCancelOrder(orderId){
        return axios({
            url: '/landlordCancelOrder',
            method: 'post',
            data: {orderId}
        })
    },
    //房东删除订单
    landlordDeleteOrder(orderId){
        return axios({
            url: '/landlordDeleteOrder',
            method: 'post',
            data: {orderId}
        })
    },
    //查询钱箱信息
    getMoney(){
        return axios({
            url: '/getMoney',
            method: 'get',
        })
    },
}
