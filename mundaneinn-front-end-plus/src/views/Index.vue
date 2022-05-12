<template>
    <el-container>
        <Header></Header>
        <Search :hide-animate="$store.state.showLoginDialog" :search-style="'bottom: -580px;'" @search="toHouse()"></Search>
        <el-header class="header-wrapper">
            <!--背景-->
            <div class="slider-wrapper">
                <div class="bg-wrapper">
                    <div class="bg-wrapper-item">
                        <div class="wrapper-item-img"></div>
                    </div>
                </div>
            </div>
        </el-header>
        <el-main class="hc-main">
            <div class="g-home-hot-layout">
                <div class="g-home-hot-wrapper">
                    <h2>热门城市</h2>
                    <ul class="hot-module">
                        <li class="hot-item" @click="selectSuggestedCity('成都市','5101')">
                            <a href=""><div class="photo-wrapper">
                                <img src="../assets/images/宽窄巷子.jpg">
                            </div></a>
                            <div class="info-wrapper">
                                <h3>巴适成都</h3>
                                <p>来这儿体验慢生活，暂别奔忙</p>
                            </div>
                        </li>
                        <li class="hot-item" @click="selectSuggestedCity('厦门市','3502')">
                            <a href=""><div class="photo-wrapper">
                                <img src="../assets/images/古都西安.jpg">
                            </div></a>
                            <div class="info-wrapper">
                                <h3>古都西安</h3>
                                <p>闲庭信步古城墙，一夜看尽长安花。</p>
                            </div>
                        </li>
                        <li class="hot-item" @click="selectSuggestedCity('西安市','6101')">
                            <a href=""><div class="photo-wrapper">
                                <img src="../assets/images/文艺厦门.jpg">
                            </div></a>
                            <div class="info-wrapper">
                                <h3>文艺厦门</h3>
                                <p>碧海白鹭，老街古厝，温柔好时光。</p>
                            </div>
                        </li>
                        <li class="hot-item" @click="selectSuggestedCity('上海市','31')">
                            <a href=""><div class="photo-wrapper">
                                <img src="../assets/images/摩登上海.jpg">
                            </div></a>
                            <div class="info-wrapper">
                                <h3>摩登上海</h3>
                                <p>体味老弄堂与摩天楼的双面风情。</p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="g-home-special-layout">
                <div class="g-home-special-wrapper">
                    <h2>超值特惠</h2>
                    <div class="special-module">
                        <div class="special-triple-wrapper">
                            <div class="left-col">
                                <a href="" class="special-card-item">
                                    <div class="photo-wrapper">
                                        <img src="../assets/images/xfyhpc2.png" alt="新房特惠">
                                    </div>
                                    <div class="info-wrapper">
                                        <h3>新房特惠</h3>
                                        <p>新房让利，抢先体验</p>
                                    </div>
                                </a>
                            </div>
                            <div class="right-col">
                                <a href="" class="special-card-item">
                                    <div class="photo-wrapper">
                                        <img src="../assets/images/jrsmpc3.png" alt="今日甩卖">
                                    </div>
                                    <div class="info-wrapper">
                                        <h3>今日甩卖</h3>
                                        <p>特价房限时购</p>
                                    </div>
                                </a>
                                <a href="" class="special-card-item">
                                    <div class="photo-wrapper">
                                        <img src="../assets/images/lzyhpc3.png" alt="连住优惠">
                                    </div>
                                    <div class="info-wrapper">
                                        <h3>连住优惠</h3>
                                        <p>住多天更优惠</p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </el-main>
        <el-footer style="padding: 0">
            <Footer></Footer>
        </el-footer>
    </el-container>
</template>

<script>
    import Footer from "@/components/Footer";
    import Header from "@/components/Header";
    import Search from "@/components/Search";
    export default {
        components: {Search, Header, Footer},
        data() {
            return {

            }
        },
        beforeCreate(){

        },
        created() {

        },
        mounted() {
        },
        beforeDestroy(){

        },
        destroyed(){

        },
        methods:{
            /*选择城市*/
            selectSuggestedCity(name,treeCode) {
                this.$store.state.search.cityName = name;
                this.$store.state.search.cityCode = treeCode;
                this.toHouse();
            },
            toHouse(){
                this.$router.push({path: "/House"})
            }
        },
    }
</script>

<style scoped>
    /*头部背景图*/
    .header-wrapper{
        height: 682px !important;/*提高权重，调整element组件头部固定高度*/
        padding: 0;
        height: 682px;
        position: relative;
        background-position: center 0;
    }
    .header-wrapper,.bg-wrapper-item{
        background-repeat: no-repeat;
        background-size: cover;
    }
    .slider-wrapper,.bg-wrapper,.bg-wrapper-item,.wrapper-item-img{
        width: 100%;
        height: 100%;
    }
    .bg-wrapper-item{
        display: flex;
        align-items: center;
        justify-content: center;
        background-position: 50%;
        background-color: #ddd;
        font-size: 0;
    }
    .wrapper-item-img{
        background-size: cover;
        background-position: 50%;
        background-image: url("../assets/images/banner.jpg");
    }

    /*内容区域*/
    img{
      display: block;
      width: 100%;
    }
    .hc-main{
        background: #fafafa;
    }
    /*热门城市*/
    .g-home-hot-layout{
        padding-top: 10px;
    }
    .g-home-hot-wrapper{
        margin: 0 auto;
        width: 1180px;
    }
    .g-home-hot-layout h2{
        font-size: 32px;
        color: #333;
        text-align: center;
        line-height: 32px;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
    .hot-module{
        margin-top: 40px;
        padding-left: 0;
        padding-bottom: 60px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
    }
    .hot-item{
        width: 280px;
        margin-right: 20px;
        position: relative;
    }
    .hot-item .photo-wrapper{
        width: 280px;
        height: 420px;
        margin-bottom: 18px;
        border-radius: 12px;
        overflow: hidden;
    }
    .info-wrapper h3{
        font-size: 24px;
        color: #333;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .info-wrapper p{
        font-size: 16px;
        color: #666;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    /*超值特惠*/
    .g-home-special-layout{
        padding-top: 0px;
    }
    .g-home-special-wrapper{
        margin: 0 auto;
        width: 1180px;
    }
    .g-home-special-wrapper h2{
        font-size: 32px;
        color: #333;
        text-align: center;
        line-height: 32px;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
    .special-module{
        margin-top: 40px;
        padding-bottom: 60px;
    }
    .special-triple-wrapper{
        width: 100%;
        height: 580px;
        display: flex;

    }
    .special-triple-wrapper{
        display: block;
        position: relative;
    }
    .special-triple-wrapper .special-card-item{
        width: 100%;
        height: 100%;
        display: block;
        position: relative;
    }
    .left-col{
        width: 580px;
        height: 580px;
        margin-right: 20px;
        float: left;
    }
    .photo-wrapper{
        width: 100%;
        height: 100%;
        overflow: hidden;
        border-radius: 12px;
    }
    .special-triple-wrapper .info-wrapper{
        position: absolute;
        top: 86px;
        left: 86px;
        max-width: 408px;
        line-height: 1;
    }
    .special-triple-wrapper .info-wrapper h3{
        font-size: 44px;
        color: #2d475f;
    }
    .special-triple-wrapper .info-wrapper p{
        margin-top: 30px;
        font-size: 26px;
        color: #5d7588;
    }
    /*今日甩卖，连住优惠*/
    .special-triple-wrapper .right-col{
        height: 580px;
        width: 580px;
        float: right;
    }
    .special-triple-wrapper .right-col .special-card-item .info-wrapper {
        position: absolute;
        top: 15px;
        left: 58px;
        max-width: 408px;
    }
    .special-triple-wrapper .right-col .special-card-item{
        height: 280px;
    }
    .special-triple-wrapper .right-col .special-card-item:last-child {
        margin-top: 20px;
    }
</style>
