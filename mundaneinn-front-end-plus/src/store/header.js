import cookie from "@/utils/auth";
import notice from "@/api/notice";
/**
 * 头部的全局数据
 */
 const header = {
    state: {
        /*显示登录弹窗*/
        showLoginDialog:cookie.getToken()==undefined?true:false,
        /*通知*/
        notice:[],
        /*未读通知总数*/
        unReadNotice:0,
    },
    mutations: {
        SET_SHOWLOGINDIALOG: (state, showLoginDialog) => {
            state.showLoginDialog = showLoginDialog
        },
        SET_NOTICE: (state, notice) => {
            state.notice = notice
        },
        SET_UNREADTOTAL:(state, unReadTotal) => {
            state.unReadNotice = unReadTotal
        },
    },
    actions:{
        //得到通知
        getNotice({commit}) {
            return new Promise((resolve, reject) => {
                if(cookie.getToken()!=undefined){
                    notice.sysNoticeList().then((res) => {
                        commit('SET_NOTICE',res.data.list)
                        commit('SET_UNREADTOTAL',res.data.unReadTotal)
                        resolve(res)
                    }).catch(error => {
                        reject(error)
                    })
                }else{
                    resolve()
                }
            })
        }
    }

 }
 export default header
