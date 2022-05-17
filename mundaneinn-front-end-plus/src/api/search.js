import axios from '@/utils/axios'
export default {
    //查询所有城市
    getAllCity(orderByPingYin){
        return axios({
            url: '/sysAreaList',
            method: 'get',
            params: {orderByPingYin}
        })
    },
    //查询地区分类
    getClientAreaTypeList(){
        return axios({
            url: '/clientAreaTypeList',
            method:'get'
        })
    },
    //查询用户常用地区
    getClientAreaPopularList(areaTypeId,treeCode){
        return axios({
            url: '/clientAreaPopularList',
            method: 'get',
            params: {areaTypeId,treeCode}
        })
    }


}
