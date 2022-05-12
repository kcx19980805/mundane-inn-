<!--头部-->
<template>
    <div>
        <!--登录弹窗-->
        <div class="login-dialog">
            <el-dialog :visible.sync="showLogin" :width="'500px'">
                <div class="m-login-wrap">
                    <div class="login-tabs">
                        <a :class="{'isActive':showMsgLogin}"
                           @click="toggleLoginMethod(true)">短信快捷登录/注册</a>
                        <a :class="{'isActive':!showMsgLogin}"
                           @click="toggleLoginMethod(false)">普通登录</a>
                    </div>
                    <div class="login-body" v-show="showMsgLogin">
                        <div class="control-group">
                            <el-input placeholder="请输入手机号" v-model="phoneNumber">
                                <template slot="prepend">+86</template>
                            </el-input>
                        </div>
                        <div class="controls">
                            <el-input placeholder="动态验证码" v-model="code"></el-input>
                            <el-button type="primary" :loading="loadingSms" @click="sendCode()">获取验证码</el-button>
                        </div>
                        <el-alert :title="errorInfo" type="error" v-if="showError" style="margin-bottom: 20px"
                                  :closable="false">
                        </el-alert>
                        <el-button type="warning" class="login-btn" @click="loginOrRegister()">登录/注册</el-button>
                    </div>
                    <div class="login-body" v-show="!showMsgLogin">
                        <div class="control-group">
                            <el-input placeholder="请输入手机号" v-model="phoneNumber">
                                <template slot="prepend">+86</template>
                            </el-input>
                        </div>
                        <div class="controls">
                            <el-input placeholder="输入密码" v-model="passWord" show-password></el-input>
                        </div>
                        <el-alert :title="errorInfo" type="error" v-if="showError" style="margin-bottom: 20px"
                                  :closable="false">
                        </el-alert>
                        <el-button type="warning" class="login-btn" @click="login()">登录</el-button>
                    </div>
                </div>
            </el-dialog>
        </div>
        <!--顶部导航-->
        <div class="hc-home-header-wrapper">
            <div class="hc-header">
                <div class="logo-area">
                    <a href="/">
                        <img src="../assets/images/logo.png" alt="红尘客栈">
                    </a>
                </div>
                <div class="slot-area">
                </div>
                <div class="tools-area">
                    <div class="tools-item" v-show="$store.getters.token==''">
                        <span @click="showLogin=true">注册</span>
                    </div>
                    <div class="tools-item" v-show="$store.getters.token==''">
                        <span @click="showLogin=true">登录</span>
                    </div>
                    <div class="tools-item" v-show="$store.getters.token != ''">
                        <el-dropdown trigger="click">
                            <div class="el-dropdown-link header-avatar">
                                <img :src="$store.getters.avatar">
                            </div>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item @click.native="toCustomerInfo()">我的信息</el-dropdown-item>
                                <el-dropdown-item @click.native="toMerchantManage()">商户系统</el-dropdown-item>
                                <el-dropdown-item @click.native="loginOut()">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                    <div class="tools-item" v-show="$store.getters.token != ''">
                        <el-dropdown trigger="click">
                            <el-badge :value="$store.getters.unReadNotice" :max="99" class="item">
                                <span class="el-dropdown-link" @click="isReadNotice()">通知</span>
                            </el-badge>
                            <el-dropdown-menu slot="dropdown" class="dropdown-notice">
                                <div class="classified-navigation">
                                    <div class="classified-navigation-item">
                                        <el-link @click="noticeType='0'" :class="{'active-notice':noticeType=='0'}">系统通知
                                        </el-link>
                                    </div>
                                    <div class="classified-navigation-item">
                                        <el-link @click="noticeType='1'" :class="{'active-notice':noticeType=='1'}">优惠促销
                                        </el-link>
                                    </div>
                                </div>
                                <div class="classified-contain">
                                    <div class="classified-contain-wrapper">
                                        <div class="classified-contain-item" v-for="(item,key) in $store.getters.notice"
                                             v-show="item.noticeType==noticeType">
                                            <a :href="item.linkUrl" style="color: black">
                                                <div class="notification-type">
                                                    <span>{{item.noticeType == "0" ? "系统通知" : "优惠促销" }}</span>
                                                    <span>{{item.createTime}}</span>
                                                </div>
                                                <el-row style="border-bottom: 1px solid #ccc">
                                                    <el-col :span="20" class="notification-describe">
                                                        {{ item.content }}
                                                    </el-col>
                                                    <el-col :span="4">
                                                        <el-button size="small" type="text" @click="deleteNotice(item.id)"><i class="el-icon-delete"></i>删除</el-button>
                                                    </el-col>
                                                </el-row>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="tips"></div>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
let Base64 = require('js-base64').Base64;
import login from "@/api/login";
import notice from "@/api/notice";

export default {
    props: [],
    data() {
        return {
            /*显示短信登录*/
            showMsgLogin: true,
            /*手机号*/
            phoneNumber: "",
            /*加载验证码*/
            loadingSms: false,
            /*短信验证码*/
            code: "",
            /*密码]*/
            passWord: "",
            /*显示错误信息*/
            showError: false,
            /*错误信息*/
            errorInfo: "",
            /*计时器*/
            timer:'',
            /*显示通知的类型*/
            noticeType: "0",
        }
    },
    created() {
        //绑定键盘按下事件
        window.addEventListener('keydown',this.keyDown);
        this.getNotice();
/*        this.timer = window.setInterval(() => {
            this.getNotice()
        },10000)*/
    },
    mounted() {

    },
    destroyed(){
        window.removeEventListener('keydown',this.keyDown,false);
        window.clearInterval(this.timer)
    },
    methods: {
        /*切换登录方式*/
        toggleLoginMethod(showMsg) {
            this.showMsgLogin = showMsg;
            this.passWord = "";
            this.code = "";
            this.showError = false
        },
        /*发送验证码*/
        sendCode() {
            if (this.phoneNumber.trim() == '') {
                this.msgError('手机号不能为空!')
                return false
            }
            const _this = this;
            this.loadingSms = true;
            //调用API
            login.sendSms(_this.phoneNumber).then(() => {
                this.msgSuccess("验证码发送成功，五分钟内有效")
                _this.loadingSms = false;
            }).catch(err=>{
                _this.loadingSms = false;
            })
        },
        /*短信登录/注册*/
        loginOrRegister() {
            const _this = this
            if (this.phoneNumber.trim() == '') {
                this.msgError('手机号不能为空!')
                return false
            }
            if (this.code.trim() == '') {
                this.msgError('验证码不能为空!')
                loading.close();
                return false
            }
            //加载提示
            const loading = this.$loading({
                lock: true,
                text: '登录中',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.3)'
            });
            this.$store.dispatch('Login', {
                account: Base64.encode(this.phoneNumber),
                password: "",
                code: this.code
            }).then((res) => {
                this.$store.commit("SET_SHOWLOGINDIALOG", false);
                loading.close();
            }).catch(err=>{
                loading.close();
            })
        },
        /*手机号登录*/
        login() {
            if (this.phoneNumber.trim() == '') {
                this.msgError("手机号不能为空")
                return false
            }
            if (this.passWord.trim() == '') {
                this.msgError('密码不能为空!')
                return false
            }
            //加载提示
            const loading = this.$loading({
                lock: true,
                text: '登录中',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.3)'
            });
            this.$store.dispatch('Login', {
                account: Base64.encode(this.phoneNumber),
                password: Base64.encode(this.passWord),
                code: ""
            }).then((res) => {
                this.$store.commit("SET_SHOWLOGINDIALOG", false);
                loading.close();
            }).catch(err=>{
                loading.close();
            })
        },
        /*退出登录*/
        loginOut() {
            this.$store.dispatch('LogOut').then(() => {
                this.$router.push({path: "/"})
            })
        },
        /*得到通知*/
        getNotice(){
            this.$store.dispatch('getNotice')
        },
        /*删除通知*/
        deleteNotice(id){
            const _this=this
            notice.deleteSysNoticeById([id]).then((res)=>{
                _this.getNotice()
            })
        },
        /*已读通知*/
        isReadNotice(){
            const _this=this
            let ids = this.$store.getters.notice.map(function (value) {
                return value.id;
            });
            notice.sysNoticeIsRead(ids).then((res)=>{
                _this.getNotice()
            })
        },
        toCustomerInfo() {
            this.$router.push({path: "/CustomerInfo"})
        },
        toMerchantManage() {
            this.$router.push({path: "/MerchantManage"})
        },
        //如果是回车则执行登录方法
        keyDown(e){
            //如果键盘按下回车并且登录页面打开
            if(e.keyCode == 13 && this.showLogin ){
                //如果是在登录/注册页面
                if(this.showMsgLogin){
                    this.loginOrRegister()
                }//如果是在仅登录页面
                else{
                    this.login()
                }
                //阻止冒泡
                return false
            }
        },
    },
    computed: {
        showLogin: {
            get() {
                return this.$store.getters.showLoginDialog;
            },
            set(val) {
                this.phoneNumber="";
                this.passWord = "";
                this.code = "";
                this.showError = false;
                this.$store.commit("SET_SHOWLOGINDIALOG", val);
            }
        },
    },
}
</script>

<style scoped>
/*头部导航*/
.hc-home-header-wrapper {
    position: absolute;
    top: 0;
    z-index: 1;
    left: 0;
    right: 0;
    background: linear-gradient(180deg, rgba(0, 0, 0, .3), transparent);
    color: #fff;
    height: 70px;
    min-width: 1180px;
    font-weight: 700;
    display: block;
}

.hc-header {
    display: flex;
    -webkit-box-pack: justify;
    justify-content: space-between;
    padding-top: 18px;
}

.logo-area {
    width: 115px;
    height: 34px;
    margin-left: 80px;
}

img {
    display: block;
    width: 100%;
}

.slot-area {
    flex-grow: 1;
    margin-left: 20px;
}

.tools-area {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    margin-right: 60px;
    height: 34px;
    line-height: 34px;
}

.tools-item {
    font-size: 14px;
    margin: 0 20px;
    cursor: pointer;
    white-space: nowrap;
}

.header-avatar {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    overflow: hidden;
}

.hc-home-header-wrapper span {
    color: #fff;
}

.dropdown-notice {
    width: 250px;
    height: 280px;
    padding: 20px;
}

.classified-navigation {
    width: 100%;
    display: flex;
    justify-content: space-around;
}

.classified-navigation-item {
    width: 80px;
    height: 20px;
    text-align: center;
}

.classified-contain {
    height: 260px;
}

.classified-contain-wrapper {
    height: 100%;
    /* auto 溢出的时候才显示滚动条 不溢出不显示滚动条 */
    overflow: auto;
}

.classified-contain-item {
    width: 95% !important;
    cursor: pointer;
    margin: 10px auto;
}

.notification-type {
    width: 100%;
    height: 20px;
    display: flex;
    justify-content: space-between;
}

.notification-describe {
    line-height: 20px;padding-top: 5px;
    margin: 0;
}

/*滚动条样式*/
*::-webkit-scrollbar {
    /*滚动条整体样式*/
    width: 5px; /*高宽分别对应横竖滚动条的尺寸*/
    height: 0px;
}

*::-webkit-scrollbar-thumb {
    /*滚动条里面小方块*/
    border-radius: 10px;
    background-color: #a2a3a5;
}

*::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #ededed;
    border-radius: 10px;
}

/*通知*/
.active-notice {
    color: #FD7A3A !important;
}

/*登录模态框*/
.m-login-wrap {
    padding: 0 40px;
    background: #fff;
}

.login-tabs {
    height: 44px;
    margin: 20px 0;
    border-bottom: 1px solid #ddd;
    font-size: 0;
    display: flex;
    justify-content: flex-start;
    overflow: hidden;
}

.isActive {
    color: #333;
    cursor: default;
    border-bottom: 2px solid #ff9645;
    text-decoration: none;
}

.m-login-wrap .login-tabs a {
    line-height: 44px;
    display: inline-block;
    width: 49%;
    font-size: 18px;
    text-align: center;
    color: #999;
    border-bottom-width: 2px;
    font-weight: bold;
    font-family: Microsoft Yahei, PingFang SC, Hiragino Sans GB, Helvetica Neue,
    Helvetica, tahoma, arial, Verdana, sans-serif, WenQuanYi Micro Hei, "\5B8B\4F53";
}

.login-body {

}

.control-group, .controls {
    margin-bottom: 20px;
}

.controls {
    position: relative;
    display: flex;
    justify-content: center;
}

.login-btn {
    width: 100%;
    height: 50px;
    background-color: #ff9645;
}
</style>
