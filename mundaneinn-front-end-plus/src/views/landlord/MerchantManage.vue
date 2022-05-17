<template>
    <section>
        <el-container>
            <el-header>
                <Header></Header>
            </el-header>
            <el-container style="height: 750px;" v-loading="loading">
                <!--侧边栏-->
                <el-aside width="250px" style="text-align: left;background-color:#545c64;">
                    <el-menu router :default-active="$route.path" :default-openeds="['0','0-1','0-2','0-3']" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" style="border-right: 0">
                        <el-submenu index="0">
                            <template slot="title">
                                <i class="el-icon-menu"></i><span>商户系统</span>
                            </template>
                            <el-submenu index="0-1">
                                <template slot="title">
                                    <span>订单管理</span>
                                </template>
                                <el-menu-item index="/MerchantManage/OrderManage">
                                    <i class="el-icon-document"></i>客户订单
                                </el-menu-item>
                            </el-submenu>
                            <el-submenu index="0-2">
                                <template slot="title">
                                    <span>房源管理</span>
                                </template>
                                <el-menu-item index="/MerchantManage/HouseList">
                                    <i class="el-icon-s-home"></i>我的房源
                                </el-menu-item>
                            </el-submenu>
                            <el-submenu index="0-3">
                                <template slot="title">
                                    <span>资金管理</span>
                                </template>
                                <el-menu-item index="/MerchantManage/CashBox">
                                    <i class="el-icon-discount"></i>钱箱
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item index="/MerchantManage/Message">
                                <div style="width: 100%;height: 30px;display:inline-block;line-height: 30px">
                                    <el-badge :value="chatUnReadTotal" :max="99" class="item">
                                        <i class="el-icon-chat-dot-round"></i>我的消息
                                    </el-badge>
                                </div>
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <!--内容区域-->
                <el-main style="background-color:#efeeec">
                    <router-view></router-view>
                </el-main>
            </el-container>
            <el-footer style="padding: 0">
                <Footer></Footer>
            </el-footer>
        </el-container>
    </section>
</template>

<script>
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import chat from "@/api/chat";
    import user from "@/api/user";
    export default {
        components: {Header,Footer},
        data(){
            return{
                chatUnReadTotal:'',
                loading:false,
                timer:''
            }
        },
        created() {
            this.getUnReadTotal();
            this.getUserInfo();
        },
        mounted(){
/*            this.timer = setInterval(() => {
                this.getUnReadTotal()
            }, 5000);*/
        },
        destroyed(){
            window.clearInterval(this.timer)
        },
        methods:{
            /*查询用户信息*/
            async getUserInfo(){
                this.loading=true
                await user.getUserInfo().then(res => {
                    if(res.data.isCertified != "1"){
                        this.msgWarning("请先实名！");
                        this.$router.push({path:"/CustomerInfo"});
                    }else{
                        this.userInfo=res.data;
                    }
                }).catch(error => {
                    this.msgError('获取用户信息失败');
                    //后退1个页面
                    this.$router.go(-1)
                })
                this.loading=false
            },
            /*查询未读消息总数*/
            getUnReadTotal(){
                chat.getChatUnReadTotal(this.$store.getters.userId).then(res=>{
                    this.chatUnReadTotal=res.data
                })
            },
        }
    }
</script>

<style scoped>
</style>
