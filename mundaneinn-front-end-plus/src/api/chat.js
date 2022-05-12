import axios from '@/utils/axios'

// 获取用户信息
export default{
    //查询2个用户之间的聊天记录
    getChatList(currentUserId,otherUserId) {
        return axios({
            url: '/clientUserChatList',
            method: 'get',
            params:{currentUserId,otherUserId}
        })
    },
    //删除聊天记录
    deleteChat(currentUserId,otherUserId){
        return axios({
            url: '/deleteClientUserChat',
            method:'post',
            data: {currentUserId,otherUserId}
        })
    },
    //已读聊天记录
    isReadChat(currentUserId,otherUserId){
        return axios({
            url: '/isReadChat',
            method:'post',
            data: {currentUserId,otherUserId}
        })
    },
    //查询与当前用户存在聊天记录的所有用户和最后一条消息
    getChatUserList(currentUserId){
        return axios({
            url: '/userList',
            method:'get',
            params: {currentUserId}
        })
    },
    //用户聊天记录未读总数
    getChatUnReadTotal(currentUserId){
        return axios({
            url: '/unReadTotal',
            method:'get',
            params: {currentUserId}
        })
    }
}
