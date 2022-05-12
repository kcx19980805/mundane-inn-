import axios from "@/utils/axios";
export  default {
    //用户余额变更记录列表
    clientUserAmountRecordList(page,limit,houseName,operator){
        return axios({
            url: '/clientUserAmountRecordList',
            method: 'get',
            params:{page,limit,houseName,operator}
        })
    },
    //用户余额变更记录-删除
    deleteAmountRecord(id){
        return axios({
            url: '/deleteAmountRecord',
            method: 'post',
            data:{id}
        })
    },
}
