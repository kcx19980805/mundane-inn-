import Vue from 'vue'
import Vuex from 'vuex'
import user from './user'
import search from './search'
import header from './header'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {

  },
  getters: {
    token: state => state.user.token,
    userId: state => state.user.userId,
    nickName: state => state.user.nickName,
    avatar: state => state.user.avatar,
    allCity: state => state.search.allCity,
    orderAllCity: state => state.search.orderAllCity,
    cityName: state => state.search.cityName,
    cityCode: state => state.search.cityCode,
    region: state => state.search.region,
    point: state => state.search.point,
    dateRange: state => state.search.dateRange,
    showLoginDialog: state=> state.header.showLoginDialog,
    notice: state=> state.header.notice,
    unReadNotice: state => state.header.unReadNotice,
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user,
    search,
    header
  }
})
