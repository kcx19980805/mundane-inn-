import axios from '@/utils/axios'

export default {
    //通知列表
    sysNoticeList() {
        return axios({
            url: '/sysNoticeList',
            method: 'get'
        })
    },//删除通知
    deleteSysNoticeById(noticeIds) {
        const data={
            noticeIds
        }
        return axios({
            url: '/deleteSysNoticeById',
            method: 'post',
            data:data
        })
    },//通知已读
    sysNoticeIsRead(noticeIds) {
        const data={
            noticeIds
        }
        return axios({
            url: '/sysNoticeIsRead',
            method: 'post',
            data:data
        })
    }
}
