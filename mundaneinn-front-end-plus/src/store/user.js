import login from '@/api/login'
import cookie from '@/utils/auth'
/**
 * 用户的全局数据
 */
const user = {
  state: {
    token: cookie.getToken()==undefined?'':cookie.getToken(),
    userId:cookie.getUserId()==undefined?'':cookie.getUserId(),
    nikeName:cookie.getNikeName()==undefined?'':cookie.getNikeName(),
    avatar:cookie.getAvatar()==undefined?'':cookie.getAvatar(),
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USERID: (state, userId) => {
      state.userId = userId
    },
    SET_NIKENAME: (state, nikeName) => {
      state.nikeName = nikeName
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const account = userInfo.account.trim()
      const password = userInfo.password
      const code = userInfo.code
      return new Promise((resolve, reject) => {
        login.login(account, password, code).then(res => {
          cookie.setToken(res.data.token)
          cookie.setUserId(res.data.userId)
          cookie.setNikeName(res.data.nikeName)
          cookie.setAvatar(res.data.headImg)
          commit('SET_TOKEN', res.data.token)
          commit('SET_USERID', res.data.userId)
          commit('SET_NIKENAME', res.data.nikeName)
          commit('SET_AVATAR', res.data.headImg)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 退出系统
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        login.logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_USERID', '')
          commit('SET_NIKENAME', '')
          commit('SET_AVATAR', '')
          cookie.removeToken()
          cookie.removeUserId()
          cookie.removeNikeName()
          cookie.removeAvatar()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

  }
}

export default user
