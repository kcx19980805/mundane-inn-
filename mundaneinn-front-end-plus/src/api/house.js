import axios from '@/utils/axios'
export default {
    //用户房源列表
    getHouseList(data) {
        return axios({
            url: '/clientHouseList',
            method: 'get',
            params: data
        })
    },
    //查询收藏房源
    getUserCollect() {
        return axios({
            url: '/userCollectList',
            method: 'get'
        })
    },
    //删除收藏房源
    deleteCollect(houseId) {
        return axios({
            url: '/deleteUserCollect',
            method: 'post',
            data: {houseId}
        })
    },
    //收藏房源
    saveCollect(houseId) {
        return axios({
            url: '/saveUserCollect',
            method: 'post',
            data: {houseId}
        })
    },
    //查询收藏房源
    getTypeMatchList() {
        return axios({
            url: '/clientTypeMatchList',
            method: 'get'
        })
    },
    //查询收藏房源
    getTypeResidenceList() {
        return axios({
            url: '/clientTypeResidenceList',
            method: 'get'
        })
    },
    //查询房源详细
    getHouseInfo(houseId) {
        return axios({
            url: '/clientHouseInfo',
            method: 'get',
            params: {houseId}
        })
    },
    //房东房源列表
    landlordHouseList(data) {
        return axios({
            url: '/landlordHouseList',
            method: 'get',
            params: data
        })
    },
    //房源新增
    saveClientHouse(data) {
        return axios({
            url: '/saveClientHouse',
            method: 'post',
            data: data
        })
    },
    //房源删除
    deleteClientHouseById(houseId) {
        return axios({
            url: '/deleteClientHouseById',
            method: 'post',
            data: {houseId}
        })
    },
    //房源修改
    updateClientHouse(data) {
        return axios({
            url: '/updateClientHouse',
            method: 'post',
            data: data
        })
    },
}
