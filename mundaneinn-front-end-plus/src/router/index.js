import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/views/Index"
import House from "@/views/customer/House";
import HouseDetail from "@/views/customer/HouseDetail";
import Order from "@/views/customer/Order";
import CustomerInfo from "@/views/customer/CustomerInfo";
import UserInfo from "@/components/customer/UserInfo";
import OrderInfo from "@/components/customer/OrderInfo";
import CollectInfo from "@/components/customer/CollectInfo";
import MessageInfo from "@/components/customer/MessageInfo";
import MerchantManage from "@/views/landlord/MerchantManage";
import OrderManage from "@/components/landlord/OrderManage";
import HouseList from "@/components/landlord/HouseList";
import CashBox from "@/components/landlord/CashBox";
import Message from "@/components/landlord/Message";
import Page401 from "@/views/error/401"
import Page404 from "@/views/error/404"
import cookie from "@/utils/auth";
import { Notification, MessageBox,Message as elementMessage} from 'element-ui'
Vue.use(VueRouter)

const routes = [
  {
    path: '',
    name: 'Index',
    component:Index,
  },
  {
    path: '/',
    name: 'Index',
    component:Index,
  },
  {
    path: '/index',
    name: 'Index',
    component:Index,
  },
  {
    path: '/House',
    name: 'House',
    component:House,
  },
  {
    path: '/HouseDetail',
    name: 'House',
    component:HouseDetail,
    props:function(route){
      return {
        houseId:route.query.houseId,
      }
    },
  },
  {
    path: '/Order',
    name: 'Order',
    component:Order,
    props:function(route){
      return {
        chooseDate:route.query.chooseDate,
        houseData:route.query.houseData,
        totalPrice:route.query.totalPrice,
      }
    },
  },
  {
    path: '/CustomerInfo',
    name: 'CustomerInfo',
    component:CustomerInfo,
    //重定向到第一个子页面避免内容空白
    redirect:"/CustomerInfo/UserInfo",
    children:[
      {
        path:'/CustomerInfo/UserInfo',
        name:"UserInfo",
        component:UserInfo,
      },
      {
        path:'/CustomerInfo/OrderInfo',
        name:"OrderInfo",
        component:OrderInfo
      },
      {
        path:'/CustomerInfo/CollectInfo',
        name:"CollectInfo",
        component:CollectInfo
      },
      {
        path:'/CustomerInfo/MessageInfo',
        name:"MessageInfo",
        component:MessageInfo
      },
    ]
  },
  {
    path: '/MerchantManage',
    name: 'MerchantManage',
    component:MerchantManage,
    //重定向到第一个子页面避免内容空白
    redirect:"/MerchantManage/OrderManage",
    children:[
      {
        path:'/MerchantManage/OrderManage',
        name:"OrderManage",
        component:OrderManage
      },
      {
        path:'/MerchantManage/HouseList',
        name:"HouseList",
        component:HouseList
      },
      {
        path:'/MerchantManage/CashBox',
        name:"CashBox",
        component:CashBox,
      },
      {
        path:'/MerchantManage/Message',
        name:"Message",
        component:Message,
      },
    ]
  },
  {
    path: '/401',
    component: Page401,
    hidden: true
  },
  {
    path: '*',
    component: Page404,
    hidden: true
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//跳转之前的全局路由守卫，所有页面跳转都会触发
router.beforeEach((to,from,next)=>{
  //如果已登录或去首页登录，放行
  if(cookie.getToken() || to.path=='/' || to.path=='/index'){
    next()
  } else{
    elementMessage({
      message: "请先登录",
      type: 'warning'
    })
    setTimeout(function (){
      location.href = '/index';
    },500)
  }
})
export default router
