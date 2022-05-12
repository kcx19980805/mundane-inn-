import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import './theme/element-variables.scss'
import animated from 'animate.css'
import BaiduMap from 'vue-baidu-map'
import myDirective from './directive'
import common from "@/utils/common"


//UI插件
Vue.use(ElementUI)
//动画插件
Vue.use(animated)
//百度地图
Vue.use(BaiduMap, {
  /* Visit http://lbsyun.baidu.com/apiconsole/key for details about app key. */
  ak: 'YOUR_APP_KEY'
})
//自定义指令
Vue.use(myDirective)

//全局方法挂载
Vue.prototype.common = common

Vue.prototype.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: "success" });
}

Vue.prototype.msgWarning = function (msg) {
  this.$message({ showClose: true, message: msg, type: "warning" });
}

Vue.prototype.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: "error" });
}

Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
}

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
