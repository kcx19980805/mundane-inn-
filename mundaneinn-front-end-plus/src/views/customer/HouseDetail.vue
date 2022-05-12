<template>
    <article class="g-unit-detail">
        <!--顶部导航-->
        <Header></Header>
        <!--轮播图-->
        <div class="unit-image">
            <el-carousel trigger="click" height="610px"
                         style="width: 1200px;margin: 20px auto 0;border: 10px solid #fdfdfd;box-sizing: border-box">
                <el-carousel-item v-for="imgItem in houseData.images">
                    <img ref="imgHeight" :src=imgItem width="100%" height="100%" object-fit="cover">
                </el-carousel-item>
            </el-carousel>
        </div>
        <!--导航-->
        <div class="detail-nav" :class="isFixedTop?'detail-nav-isFixed':''">
            <div class="unit-navbar">
                <div class="unit-navbar__container">
                    <div class="unit-navbar__container__main">
                        <ul class="unit-navbar__nav">
                            <li class="unit-navbar__nav__item">
                                <a :class="navIndex==0?'item--select':''" @click="scroll(0)">房屋介绍</a>
                            </li>
                            <li class="unit-navbar__nav__item">
                                <a :class="navIndex==1?'item--select':''" @click="scroll(1)">交易规则</a>
                            </li>
                            <li class="unit-navbar__nav__item">
                                <a :class="navIndex==2?'item--select':''" @click="scroll(2)">入住须知</a>
                            </li>
                            <li class="unit-navbar__nav__item">
                                <a :class="navIndex==3?'item--select':''" @click="scroll(3)">房屋点评</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!--内容-->
        <div class="detail-container">
            <!--左边的内容-->
            <div class="detail-container__left">
                <!--房源地址-->
                <div class="detail-container__main">
                    <div>
                        <div class="unit-title">
                            <h3 class="unit-title__name">
                                {{ houseData.name }}
                            </h3>
                            <address class="unit-title__address">
                                地址：{{houseData.location}}
                            </address>
                        </div>
                        <div class="unit-house-info">
                            <ul>
                                <li>
                                    <i class="icon-fangwuleixing"></i>
                                    <span>{{houseData.residenceType=="0"?'小区住宅':'普通公寓'}}</span>
                                </li>
                                <li>
                                    <i class="icon-huxing"></i>
                                    <span>{{houseData.houseType}}室{{houseData.parlorNumber}}厅{{houseData.bathroomNumber}}卫</span>
                                </li>
                                <li>
                                    <i class="icon-chuangxing"></i>
                                    <span>{{ houseData.bedNumber }}张床</span>
                                </li>
                                <li>
                                    <i class="icon-yizhujiren"></i>
                                    <span>宜住{{ houseData.peopleNumber }}人</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!--房源描述-->
                <div class="detail-container__main">
                    <h1> 房源描述 </h1>
                    <div class="unit-description">
                        <div v-html="houseData.describe"></div>
                    </div>
                </div>
                <!--设施服务-->
                <div class="detail-container__main">
                    <h1>设施服务</h1>
                    <div class="unit-facilities">
                        <ul class="simple-version">
                            <li>
                                <h2 class="facilities-title">配套设施</h2>
                            </li>
                            <li>
                                <el-checkbox v-for="(item,index) in matchingList" class="facilities-item" :checked="houseData.matchList.some(e=>e==item.id)" disabled><i class="icon-match"></i>{{item.name}}</el-checkbox>
                            </li>
                            <li>
                                <h2 class="facilities-title">房型</h2>
                            </li>
                            <li>
                                <el-checkbox v-for="(item,index) in houseList" class="facilities-item" :checked="houseData.residenceList.some(e=>e==item.id)" disabled>{{item.name}}</el-checkbox>
                            </li>
                        </ul>
                    </div>
                </div>
                <!--房源位置-->
                <div class="detail-container__main">
                    <h1> 房源位置 </h1>
                    <div class="unit-local">
                        <div id="map" style="width:100%;height:400px;"></div>
                    </div>
                </div>
                <!--交易规则-->
                <div class="detail-container__main">
                    <h1>交易规则</h1>
                    <div class="unit-trade__all">
                        <ul class="trade-lists">
                            <li>
                                <span>付款方式：</span>
                                <div class="rules__intro">全额预付房费</div>
                            </li>
                            <li>
                                <span>无须确认：</span>
                                <div class="rules__intro">下单后即有房，无需等待房东确认</div>
                            </li>
                            <li>
                                <span>入住押金：</span>
                                <div class="rules__intro">免收</div>
                            </li>
                            <li>
                                <span>清洁费：</span>
                                <div class="rules__intro">一客一扫，在线收取清洁费25元，已包含在当前价格中</div>
                            </li>
                        </ul>
                        <el-steps :active="2" align-center style="margin-left: -80px;">
                            <el-step title="预定成功" icon="el-icon-success" description="支付费用"></el-step>
                            <el-step title="入住之前" icon="el-icon-house" description="取消订单全额退费"></el-step>
                            <el-step title="入住当天" icon="el-icon-s-home" description="取消订单扣除全部房费"></el-step>
                            <el-step title="离店后" icon="el-icon-office-building" description="最晚13:00前离店"></el-step>
                        </el-steps>
                    </div>
                </div>
                <!--入住须知-->
                <div class="detail-container__main">
                    <h1> 入住须知 </h1>
                    <div style="display: flex">
                        <ul class="services-lists">
                            <li><span>入住时间：</span><div>15:00后入住</div></li>
                            <li><span>退房时间：</span><div>12:00前退房</div></li>
                            <li><span>自助入住：</span><div>通过智能门锁自助入住</div></li>
                            <li><span>提供发票：</span><div>开具发票请与房东协商</div></li>
                            <li><span>卫生打扫：</span><div>1客1扫</div></li>
                            <li><span>卫生打扫：</span><div>1客1扫</div></li>
                            <li><span>被单更换：</span><div>1客1换</div></li>
                            <li><span>洗漱更换：</span><div>1客1换</div></li>
                            <li><span>毛巾更换：</span><div>1客1换</div></li>
                        </ul>
                    </div>
                </div>
                <!--评论-->
                <div class="detail-container__main comment">
                    <div class="unit-comment">
                        <div class="unit-comment__nav">
                            <span :class="{'nav--choose':!isLandlord}" @click="getComments(false)">本房屋点评{{!isLandlord?'（'+total+'条）':'（）条'}}</span>
                            <span :class="{'nav--choose':isLandlord}" @click="getComments(true)">房东所有点评{{isLandlord?'（'+total+'条）':'（）条'}}</span>
                        </div>
                        <div class="one-px__height"></div>
                        <div class="unit-comment__container">
                            <div class="unit-comment__container__rate">
                                <span>综合评分：</span>
                                <el-rate
                                        v-model="score"
                                        disabled
                                        show-score
                                        text-color="#ff9900"
                                        score-template="{value}"
                                style="width: 150px; float: left">
                                </el-rate>
                                <span>分</span>
                            </div>
                            <!--评论列表-->
                            <ul>
                                <li class="unit-comment__container__item" v-for="item in comments">
                                    <div class="unit-comment__container__item__top">
                                        <img :src="item.headImg" alt=""/>
                                        <div>
                                            <div>{{item.phone.replace(/(\d{3})\d{4}(\d{4})/, "$1****$2")}} </div>
                                            <div>{{item.createTime}}入住</div>
                                        </div>
                                    </div>
                                    <p>{{item.content}}</p>
                                    <div class="one-px__border"></div>
                                </li>
                            </ul>
                            <!--页码条-->
                            <div class="unit-comment__container__page">
                                <el-pagination
                                        background
                                        layout="prev, pager, next"
                                        :total="total"
                                        :page-size="pageSize"
                                        :current-page="currentPage"
                                        @current-change="pageChange"
                                         style="float: right">
                                </el-pagination>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--右边的浮动，预定功能-->
            <div id="rightside" :class="isFixedTop?'g-unitList-selected-isFixed':'detail-container__right'">
                <div class="unit-price" v-loading="loading">
                    <div class="unit-price__price">
                        <span class="unit-price__price--final"><span>￥</span><span
                            class="price__count">{{ houseData.housePrice }}</span>每晚</span>
                    </div>
                    <div class="unit-price__calandar">
                        <el-date-picker format="yyyy-MM-dd"
                                        value-format="yyyy-MM-dd"
                                        v-model="chooseDate"
                                        type="daterange"
                                        range-separator="-"
                                        start-placeholder="开始日期"
                                        end-placeholder="结束日期"
                                        :picker-options="pickerOptions"
                                        style="width: 100%"
                                        @change="totalPrice=common.dateDiff(chooseDate[0],chooseDate[1])*houseData.housePrice"
                        >
                        </el-date-picker>
                    </div>
                    <button class="common__button" @click="toOrderInfo" >
                        <span class="common__button_price">
                            立刻预订（{{chooseDate == '' ? '0' : common.dateDiff(chooseDate[0], chooseDate[1])}}晚¥{{ totalPrice }}）
                        </span>
                    </button>
                </div>
                <div class="unit-contact">
                    <div class="unit-contact__land">
                        <img :src="houseData.headImg"
                             class="unit-contact__land__photo" alt="">
                        <div class="unit-contact__land__main">
                            <a>{{ houseData.userName+' '+ houseData.phone }}</a>
                            <p>
                                <span>商户经营</span><span> · 营业执照已验证</span>
                            </p>
                        </div>
                    </div>
                    <div class="unit-contact__contact">
                        <div class="unit-contact__contact__phone">
                            <el-tooltip :content="'请拔打电话'+houseData.phone" placement="bottom" effect="light">
                                <el-button type="text">拨打电话</el-button>
                            </el-tooltip>
                        </div>
                        <div>
                            <el-button type="text" @click="chat()">在线咨询</el-button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <Chat :is-show.sync="showChat" v-if="houseData!=''" :user-id="houseData.userId" :chat-style="'right: 150px; bottom: 250px;'"></Chat>
        <Footer></Footer>
    </article>
</template>

<script>
    import Footer from "@/components/Footer";
    import Header from "@/components/Header";
    import Chat from "@/components/Chat";
    import house from "@/api/house";
    import order from "@/api/order";
    import comment from "@/api/comment";
    export default {
        components: {Footer,Chat, Header},
        props: ['houseId'],
        data() {
            return {
                /*当前导航索引*/
                navIndex:0,
                /*导航内容高度列表*/
                navList: [],
                /*导航和预定是否固定在顶部*/
                isFixedTop: false,
                /*配套数据*/
                matchingList:[],
                /*房型数据*/
                houseList:[],
                /*房源数据*/
                houseData: '',
                /*评论数量*/
                total: 0,
                /*每页显示的评论数量*/
                pageSize: 10,
                /*当前页码*/
                currentPage: 1,
                /*评论数据*/
                comments: [],
                /*评分*/
                score:0,
                /*房东点评*/
                isLandlord:false,
                /*总价*/
                totalPrice: 0,
                /*显示聊天框*/
                showChat: false,
                /*限制选择时间范围*/
                pickerOptions: {},
                /*当前用户选择的日期*/
                chooseDate: '',
                /*房源已有订单的日期*/
                dateSet: [],
                /*计时器*/
                interval:'',
                /*显示加载中*/
                loading:false,
            }
        },
        beforeCreate() {

        },
        created() {
        },
        async mounted() {
            this.loading=true;
            await Promise.all([
                //查询已存在的订单
                this.getOrder(),
                //查询房源信息
                this.getHouseDetail()
            ]).then(res => {
                //限制选定的日期
                const _this = this;
                this.pickerOptions = {
                    //禁用日期
                    disabledDate(time) {
                        //限制在今天以后，且在出租时段内的时间
                        if (time.getTime() < Date.now() || time.getTime() < (new Date(_this.houseData.startDate - 1000 * 3600 * 12)) ||
                            time.getTime() > new Date(_this.houseData.endDate)) {
                            return true;
                        }
                        //限制没有订单的时间
                        for (let i = 0; i < _this.dateSet.length; i++) {
                            if (time.getTime() >= (new Date(_this.dateSet[i].startDate) - 1000 * 3600 * 14) &&
                                time.getTime() < new Date(_this.dateSet[i].endDate) - 1000 * 3600 * 12) {
                                return true
                            }
                        }
                    }
                }
                this.loading=false;
                //得到配套
                this.getMatchList();
                //得到房型
                this.getResidenceList();
                //得到评论
                this.getComments()
            })
            //获得内容区高度,700是容器父元素到顶部的距离
            let content= document.getElementsByClassName("detail-container__main");
            this.navList.push(content[0].offsetTop+700)
            this.navList.push(content[3].offsetTop+700)
            this.navList.push(content[4].offsetTop+700)
            this.navList.push(content[5].offsetTop+700)
            // 滚动条的获取
            window.addEventListener('scroll', this.handleScroll, true)
        },
        methods: {
            //滚动屏幕
            scroll(index) {
                this.interval = window.setInterval(() => {
                    this.incrScroll(index)
                }, 5);
            },
            /*逐渐滚动*/
            incrScroll(index){
                let boxYOffset=this.navList[index];
                let currentYOffset=window.pageYOffset;
                if(Math.abs(boxYOffset-currentYOffset)>20){
                    if(currentYOffset < boxYOffset){
                        currentYOffset = currentYOffset + 20;
                    }else if(currentYOffset > boxYOffset){
                        currentYOffset = currentYOffset - 20;
                    }
                    //滚动条滑动到指定坐标X,Y
                    window.scrollTo(0, currentYOffset);
                } else{
                    window.clearInterval(this.interval)
                }
            },
            /*下拉滚动条时将日期选择固定在顶部*/
            handleScroll() {
                //下拉滚动条时将日期选择固定在顶部
                if (window.pageYOffset >= 676) {
                    this.isFixedTop = true
                }
                else if (window.pageYOffset < 676) {
                    this.isFixedTop = false
                }
                //设置导航
                let pageYOffset=window.pageYOffset+100;
                if (pageYOffset >= this.navList[3]) {
                    this.navIndex = 3
                } else if (pageYOffset >= this.navList[2]) {
                    this.navIndex = 2
                } else if (pageYOffset >= this.navList[1]) {
                    this.navIndex = 1
                } else if (pageYOffset >= this.navList[0]) {
                    this.navIndex = 0
                }
            },
            /*查询配套列表*/
            getMatchList(){
                house.getTypeMatchList().then(res=>{
                    this.matchingList=res.data;
                })
            },
            /*查询房型列表*/
            getResidenceList(){
                house.getTypeResidenceList().then(res=>{
                    this.houseList=res.data;
                })
            },
            /*得到房源已存在的订单*/
            getOrder() {
                return new Promise((resolve, reject) => {
                    order.getExistedOrder(this.houseId).then(res=>{
                        this.dateSet=res.data;
                        resolve();
                    }).catch(()=>{
                        reject();
                    })
                });
            },
            /*房源详情*/
            getHouseDetail() {
                return new Promise((resolve, reject) => {
                    house.getHouseInfo(this.houseId).then(res=>{
                        this.houseData=res.data;
                        this.score=new Number(this.houseData.score);
                        //创建Map实例
                        let map = new BMap.Map("map");
                        map.centerAndZoom(new BMap.Point(this.houseData.pointLng,this.houseData.pointLat),15);
                        //开启鼠标滚轮缩放
                        map.enableScrollWheelZoom(true);
                        let opts = {
                            // 信息窗口宽度
                            width: 150,
                            title: "房源位置"
                        }
                        //创建信息窗口对象
                        let infoWindow = new BMap.InfoWindow(this.houseData.location, opts);
                        map.openInfoWindow(infoWindow, map.getCenter());
                        resolve();
                    }).catch(()=>{
                        reject();
                    })
                });
            },
            /*得到房屋评论*/
            getComments(isLandlord) {
                this.isLandlord=isLandlord;
                let landlordId;
                if(isLandlord){
                    landlordId=this.houseData.userId;
                }
                comment.getCommentList(this.houseId,landlordId,this.currentPage,this.pageSize).then(res=>{
                    this.comments=res.data.list;
                    this.total=res.data.total;
                })
            },
            /*评论换页*/
            pageChange(Page) {
                this.currentPage = Page
                this.getComments()
            },
            //打开聊天框
            chat() {
                if (this.$store.getters.userId == this.houseData.userId) {
                    this.msgWarning('不能和自己聊天')
                    return false;
                }
                this.showChat = true
            },
            //去订单页
            toOrderInfo() {
                if (this.chooseDate == '' || this.totalPrice == 0) {
                    this.msgError('请选择日期')
                    return false;
                }
                this.$router.push({
                    path:"/Order",
                    query:{
                        houseData: JSON.stringify(this.houseData),
                        totalPrice: this.totalPrice,
                        chooseDate: this.chooseDate
                    }
                })
            }
        }
    }
</script>

<style scoped>
.g-unit-detail {
    width: 100%;
    min-width: 1280px;
    position: relative;
    background-color: #f5f5f5;
    --main-price-token: dHVqaWFjc3Mw;
    line-height: 1.4;
    font-size: 14px;
}

/*背景大图*/
.unit-image {
    height: 670px;
    width: 100%;
    min-width: 1280px;
    position: relative;
    color: #fff;
    background-position: 50%;
    background-size: cover;
    background-repeat: no-repeat;
    display: inline-block;
    background-color: #f5f5f5;
    margin-top: 80px;
}

.unit-image p {
    display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-top: 50px;
    text-align: left;
    margin-left: 20px;
}

/*导航*/
.detail-nav {
    width: 100%;
    margin: 0 auto;
    position: relative;
    z-index: 1;
    top: 0px;
}

/*固定导航*/
.detail-nav-isFixed {
    position: fixed;
}

.unit-navbar {
    width: 1200px;
    margin: 0 auto;
    position: relative;
}

.unit-navbar .unit-navbar__container {
    width: 100%;
    min-width: 1000px;
    background: #fff;
    position: static;
    -webkit-box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .05);
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .05);
}

.unit-navbar__container__main {
    width: 1200px;
    margin: 0 auto;
    background: #fff;
    height: 56px;
    color: #333;
    cursor: pointer;
}

.unit-navbar__container__main .unit-navbar__nav {
    float: left;
    margin: 0;
    padding: 0;
}

.unit-navbar__container__main .unit-navbar__nav .unit-navbar__nav__item {
    float: left;
    margin: 0 15px;
    height: 56px;
    line-height: 56px;
}

.unit-navbar__container__main .unit-navbar__nav .unit-navbar__nav__item .item--select {
    font-weight: 600;
    font-family: PingFangSC-Regular;
    color: #fd8238;
}

/*内容*/
.detail-container {
    position: relative;
    width: 1200px;
    margin: 0 auto;
    display: flex;
}

.detail-container .detail-container__left {
    width: 810px;
    margin-top: 30px;
    margin-right: 30px;
    float: left;
}

.detail-container__left .detail-container__main {
    margin-bottom: 20px;
    background: #fff;
    padding: 10px 20px 20px;
    border-radius: 5px;
    overflow: hidden;
}

/*标题*/
.unit-title .unit-title__name {
    font-size: 28px;
    color: #333;
    margin-bottom: 10px;
    margin-top: 10px;
}

.unit-title .unit-title__address {
    color: #9b9b9b;
    line-height: 20px;
    font-size: 18px;
    margin-bottom: 20px;
    display: block;
    margin: 0;
    padding: 0;
}

address {
    font-style: normal;
    font-weight: 400;
}

/*房屋信息*/
.unit-house-info {
    margin-top: 20px;
    background: transparent;
    display: inline-block;
}

.unit-house-info ul {
    display: inline-block;
    margin: 0;
    padding: 0;
}

.unit-house-info ul li {
    position: relative;
    float: left;
    width: 76px;
    text-align: center;
}

/*精灵图片*/
.unit-house-info ul li i {
    display: block;
    text-align: center;
    width: 28px;
    height: 28px;
    margin: 0 auto 15px;
}
/*房屋分类*/
.icon-fangwuleixing, .icon-guanjinglutai {
    background-image: url("../../assets/images/houseInfo-icon.1308d999.png");
    width: 28px;
    height: 28px;
    background-position: -64px 0;
}
/*户型*/
.icon-huxing{
    background-image: url("../../assets/images/houseInfo-icon.1308d999.png");
    width: 28px;
    height: 28px;
    background-position: 0 0;
}
/*床型*/
.icon-chuangxing {
    background-image: url("../../assets/images/houseInfo-icon.1308d999.png");
    width: 28px;
    height: 28px;
    background-position: -64px -64px;
}

/*宜住几人*/
.icon-yizhujiren {
    background-image: url("../../assets/images/houseInfo-icon.1308d999.png");
    width: 28px;
    height: 28px;
}

.icon-yizhujiren {
    background-position: -96px -96px;
}

/*独立卫浴*/
.icon-dulidanjian, .icon-duliweiyu {
    background-image: url("../../assets/images/houseInfo-icon.1308d999.png");
    width: 28px;
    height: 28px;
}

.icon-duliweiyu {
    background-position: -32px -32px;
}

.unit-description {
    white-space: pre-line;
    font-size: 14px;
    color: #333;
    line-height: 26px;
    letter-spacing: 0;
    text-overflow: ellipsis;
    display: -webkit-box;
    overflow: hidden;
    -webkit-box-orient: vertical;
    text-align: left;
}

.unit-facilities ul li {
    margin-bottom: 10px;
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;/*wrap规定为多行显示*/
}

.unit-facilities ul li .facilities-title {
    font-size: 14px;
    color: #333;
    margin-bottom: 16px;
    font-weight: 800;
    text-align: left;
}
.unit-facilities ul li .facilities-item{
    width: 120px;
    text-align: left;
    margin-bottom: 10px;
}
/*多选框禁用时的颜色*/
.unit-facilities>>> .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner {
    background-color: #FD7A3A !important;
    border-color: #FD7A3A !important;
}

.unit-facilities>>> .el-checkbox__input.is-disabled+span.el-checkbox__label {
    color: #606266 !important;
    cursor: not-allowed;
}
/*配套和房型的图片*/
.unit-facilities ul li i {
    width: 20px;
    height: 20px;
    display: inline-block;
    background-repeat: no-repeat;
    margin-right: 13px;
    vertical-align: middle;
    background-image: url("../../assets/images/houseService-icon.ab820730.png");
}

/*精灵图片*/
/*wifi*/
.facilities-item:nth-of-type(1) i{
    background-position: -192px -216px;
}
/*电梯*/
.facilities-item:nth-of-type(2) i {
    background-position: -22px 0px;
}
/*热水淋浴*/
.facilities-item:nth-of-type(3) i {
    background-position: -144px -72px;
}
/*洗衣机*/
.facilities-item:nth-of-type(4) i {
    background-position: -96px -216px;
}
/*电视*/
.facilities-item:nth-of-type(5) i {
    background-position: -216px -192px;
}
/*空调*/
.facilities-item:nth-of-type(6) i {
    background-position: 0 -24px;
}
/*浴缸*/
.facilities-item:nth-of-type(7) i  {
    background-position: -192px -50px;
}
/*冰箱*/
.facilities-item:nth-of-type(8) i  {
    background-position: -144px -120px;
}
/*卫浴用品*/
.facilities-item:nth-of-type(9) i {
    background-position: -144px 0;
}
/*投影设备*/
.facilities-item:nth-of-type(10) i {
    background-position: -95px -24px;
}
/*麻将机*/
.facilities-item:nth-of-type(11) i {
    background-position: -192px -120px;
}
/*免费停车*/
.facilities-item:nth-of-type(12) i {
    background-position: -192px -73px;
}
/*毛巾*/
.facilities-item:nth-of-type(13) i {
    background-position: -216px -168px;
}
/*牙具*/
.facilities-item:nth-of-type(14) i{
    background-position: -216px -144px;
}
/*独立卫浴*/
.facilities-item:nth-of-type(15) i{
    background-position: 0 -144px;
}

/*地图*/

/*交易规则*/
.unit-trade__all .trade-lists {
    position: relative;
}

.unit-trade__all .trade-lists > li {
    letter-spacing: 0;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    position: relative;
    margin-bottom: 14px;
}

/*入住须知*/
.services-lists{
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    width: 100%;
}
.services-lists > li {
    margin: 0 0 14px;
    display: flex;
    min-width: 220px;
}
/*评论*/
.unit-comment {
    padding: 0 30px;
    background: #fff;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .05);
}
.unit-comment__nav {
    box-sizing: border-box;
    height: 60px;
    padding-top: 22px;
    cursor: pointer;
}

.unit-comment__nav span  {
    margin-right: 40px;
}
.nav--choose{
    color: #fd8238;
}

/*分割线*/
.one-px__height {
    width: 810px;
    height: 1px;
    background: #eee;
    margin-left: -30px;
}

/*评分*/
.unit-comment .unit-comment__container {
    margin-top: 30px;
}

.unit-comment .unit-comment__container .unit-comment__container__rate {
    margin-bottom: 15px;
    line-height: 24px;
    position: relative;
    color: #666;
    display: flex;
    justify-content: flex-start;
}

.unit-comment__container__rate > span {
    font-size: 18px;
}

.unit-comment__container__rate > span:last-child {
    font-size: 14px;
    height: 20px;
    line-height: 20px;
    margin-left: 4px;
    bottom: 1px;
}

/*评论列表*/
.unit-comment__container__item{
    text-align: left;
}

.unit-comment__container__item__top {
    display: flex;
}

.unit-comment__container__item__top img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 15px;
    margin-top: 1.5px;
    border: 0;
    vertical-align: middle;
}

comment__container__item__top > div {
    color: #999;
    font-size: 12px;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
}

comment__container__item__top > div p:first-child {
    font-size: 16px;
    color: #333;
    margin-bottom: 6px;
    height: 21px;
    line-height: 21px;
}

comment__container__item__top > div p:last-child {
    height: 16px;
    line-height: 16px;
}

.unit-comment__container__item > p {
    margin-top: 16px;
    padding-left: 55px;
    font-size: 14px;
    color: #333;
    letter-spacing: 0;
    line-height: 26px;
}

.unit-comment__container__item .one-px__border {
    margin: 0 0 10px 55px;
    border-bottom: 1px dotted #ddd;
}

/*页码条*/
.unit-comment .unit-comment__container .unit-comment__container__page {
    padding-bottom: 42px;
    overflow: hidden;
}

.unit-comment__container__page >>> .el-pagination.is-background .el-pager li:not(.disabled).active {
    background-color: #FD7A3A;
    color: #fff;
}

/*预定*/

/*固定在顶部*/
.g-unitList-selected-isFixed {
    width: 360px;
    position: fixed;
    z-index: 1;
    top: 60px;
    margin-left: 830px;
}

.unit-price {
    border: 1px solid #f2f2f2;
    border-bottom: 0;
    width: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding: 30px 20px;
    background: #fff;
    position: relative;
}

.unit-price .unit-price__price {
    margin-bottom: 20px;
    display: flex;
    justify-content: flex-start;
}

.unit-price__price .unit-price__price--final {
    font-size: 14px;
    color: #333;
    line-height: 12px;
    letter-spacing: 0;
}

.unit-price__price--final > span {
    font-weight: 500;
}

.unit-price__price--final .price__count {
    font-family: tjFont;
    font-size: 36px;
    text-align: right;
    line-height: 34px;
    margin-right: 5px;
    font-weight: 600;
}

/*日期*/
.unit-price__calandar {
    margin-bottom: 20px;
    position: relative;
    text-align: left;
    color: #FD7A3A;
}

.unit-price .common__button {
    text-align: center;
    white-space: nowrap;
    width: 100%;
    height: 50px;
    line-height: 50px;
    box-sizing: border-box;
    outline: none;
    border: 0;
    font-size: 16px;
    margin: 0;
    color: #fff;
    background-image: linear-gradient(-221deg, #ff721e, #ff9b3e);
    cursor: pointer;
}

.common__button_price {
    font-size: 18px;
    line-height: 18px;
    display: inline-block;
    font-family: PingFangSC-Medium;
    letter-spacing: 0;
}

/*商家信息*/
.unit-contact {
    position: relative;
    width: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    background: #fff;
    border: 1px solid #f2f2f2;
}

.unit-contact .unit-contact__land {
    padding: 30px 20px 20px;
    display: flex;
    cursor: pointer;
}

.unit-contact .unit-contact__land .unit-contact__land__photo {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    overflow: hidden;
    margin-right: 20px;
    border: 0;
    vertical-align: middle;
}

.unit-contact .unit-contact__land .unit-contact__land__main {
    overflow: hidden;
    height: 45px;
    margin-top: 7px;
    text-overflow: ellipsis;
}

.unit-contact .unit-contact__land .unit-contact__land__main a {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    letter-spacing: 0;
    font-size: 16px;
    line-height: 24px;
    font-weight: 500;
    color: #333;
    text-decoration: none;
    cursor: pointer;
}

.unit-contact .unit-contact__land .unit-contact__land__main p {
    padding-top: 3px;
    font-size: 12px;
    color: #666;
    margin: 0;
    padding: 0;
}

.unit-contact .unit-contact__land .unit-contact__land__main p span {
    letter-spacing: 0;
    line-height: 18px;
    position: relative;
}

/*拨打电话/在线咨询*/
.unit-contact .unit-contact__contact {
    height: 60px;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    cursor: pointer;
    background: #fbfbfb;
    border-top: 1px solid #f2f2f2;
}

.unit-contact .unit-contact__contact .unit-contact__contact__phone {
    color: #666;
    border-right: 1px solid #f2f2f2;
}

.unit-contact .unit-contact__contact > div {
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    text-align: center;
    font-size: 14px;
    letter-spacing: 0;
    line-height: 60px;
}

</style>
