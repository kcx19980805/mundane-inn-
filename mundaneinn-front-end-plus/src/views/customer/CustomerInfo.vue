<template>
    <section>
        <el-container>
            <el-header>
                <Header></Header>
            </el-header>
            <el-container style="height: 750px;">
                <!--侧边栏-->
                <el-aside width="250px" style="text-align: left;background-color:#545c64;">
                    <el-menu router :default-active="$route.path" :default-openeds="['0']" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" style="border-right: 0">
                        <el-submenu index="0">
                            <template slot="title">
                                <i class="el-icon-menu" ></i><span>个人中心</span>
                            </template>
                            <el-menu-item index="/CustomerInfo/UserInfo"  class="second-menu">
                                <i class="el-icon-user-solid"></i>个人信息
                            </el-menu-item>
                            <el-menu-item index="/CustomerInfo/OrderInfo" class="second-menu">
                                <i class="el-icon-tickets"></i>我的订单
                            </el-menu-item>
                            <el-menu-item index="/CustomerInfo/CollectInfo" class="second-menu">
                                <i class="el-icon-star-off"></i>我的收藏
                            </el-menu-item>
                            <el-menu-item index="/CustomerInfo/MessageInfo" class="second-menu">
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
    export default {
        components: {Header,Footer},
        data(){
            return{
                chatUnReadTotal:'',
                timer:''
            }
        },
        created() {
            this.getUnReadTotal();
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
    img {
        display: block;
        width: 100%;
    }
    .second-menu{
        /*background-color: #172738 !important;*/
    }
</style>
