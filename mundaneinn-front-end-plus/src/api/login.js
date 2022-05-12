import axios from '@/utils/axios'
export default {
    // 登录方法
    login(account, password, code) {
        const data = {
            account,
            password,
            code
        }
        return axios({
            url: '/login',
            method: 'post',
            data: data
        })
    },
    // 退出方法
    logout() {
        return axios({
            url: '/logOut',
            method: 'post'
        })
    },
    // 发送验证码
    sendSms(phone) {
        return axios({
            url: '/sendSms',
            method: 'post',
            data: {phone}
        })
    }
}


