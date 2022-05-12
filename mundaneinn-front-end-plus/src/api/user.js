import axios from '@/utils/axios'

// 获取用户信息
export default{
    //查询当前用户信息
    getUserInfo() {
        return axios({
            url: '/getUserInfo',
            method: 'get'
        })
    },
    //查询指定用户信息
    getOtherUserInfo(userId){
        return axios({
            url: '/getOtherUserInfo',
            method:'get',
            params:{userId}
        })
    },
    //修改用户信息
    updateUserInfo(data){
        return axios({
            url: '/updateUserInfo',
            method:'post',
            data:data
        })
    },
    //修改密码
    updateUserPassword(data){
        return axios({
            url: '/updatePassword',
            method:'post',
            data:data
        })
    }
}
