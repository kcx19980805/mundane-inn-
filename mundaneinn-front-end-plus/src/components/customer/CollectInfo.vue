<!--收藏-->
<template>
    <el-row style="height: 100%;overflow: scroll;background-color: white" v-loading="loading">
        <div v-if="houseData.length==0" class="el-table__empty-block" style="width: 100%; height: 100%;"><span class="el-table__empty-text">暂无数据</span></div>
        <!--房屋子项-->
        <section class="tj-unit-list clearfix">
            <article class="tj-unit-item-layout"  v-for="(item,key) in houseData" :key="key" >
                <div class="tj-unit-item">
                    <div class="unit-item-pic-wrapper">
                        <div class="swiper-container pic-swiper swiper-container-horizontal" @click="toDetail(item.id)">
                            <el-carousel trigger="click" :autoplay="false" height="296px">
                                <el-carousel-item v-for="imgItem in item.images">
                                    <img ref="imgHeight" :src=imgItem  width="100%" height="100%" object-fit="cover">
                                </el-carousel-item>
                            </el-carousel>
                        </div>
                        <i class="collection-icon icon-collection" @click="cancelCollection(item.id)"></i>
                    </div>
                    <div class="unit-item-content" @click="toDetail(item.id)">
                        <h3 class="unit-title">
                            <a>{{item.name}}</a>
                        </h3>
                        <ul class="unit-desc-list">
                            <li><span>{{item.rentalType==0?'单间':'整套'}}</span></li>
                            <li><span>{{item.houseType}}居{{item.bedNumber}}床{{item.peopleNumber}}人</span></li>
                            <li><span>{{item.score}}分/{{item.commentsNumber}}点评</span></li>
                            <li v-if="item.distance != ''"><span>{{'距离'+new Number(item.distance).toFixed(2)+'公里'}}</span></li>
                        </ul>
                        <div class="unit-desc-list-extra">
                            <ul class="unit-little-label-list">
                                <li class="unit-little-label-list-item" v-show="item.isNearSubway=='1'">近地铁</li>
                                <li class="unit-little-label-list-item">宽松取消</li>
                                <li class="unit-little-label-list-item">立即确认</li>
                            </ul>
                        </div>
                        <div class="unit-price-content">
                            <p class="unit-price">
                                <span class="text">￥{{item.housePrice}}</span>
                                <span class="text">/</span>
                                <span>晚</span>
                            </p>
                        </div>
                        <section class="tj-avatar-layout landlord-avatar">
                            <img :src="item.headImg" alt="" class="avatar-img">
                        </section>
                    </div>
                </div>
            </article>
        </section>
    </el-row>
</template>

<script>
    import house from "@/api/house";
    export default {
        name: "Collection",
        data(){
            return{
                houseData:[],
                loading:false,
            }
        },
        created() {
            this.getCollection()
        },
        methods:{
            /*得到收藏*/
            getCollection(){
                this.loading=true;
                house.getUserCollect().then(res=>{
                    this.houseData=res.data;
                    this.loading=false;
                })
            },
            /*取消收藏*/
            cancelCollection(houseId){
                this.loading=true;
                house.deleteCollect(houseId).then(res=>{
                    this.getCollection();
                })
            },
            /*查看详情*/
            toDetail(houseId){
                this.$router.push({
                    path:"/HouseDetail",
                    query:{
                        houseId:houseId,
                    }
                })
            },
        }

    }
</script>

<style scoped>
    /*房屋内容项区域*/
    .tj-unit-list{
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    .tj-unit-item-layout{
        display: block;
        padding: 0 10px;
        width: 25%;
        min-width: 400px;
    }
    @media screen and (max-width: 1024px){
        .tj-unit-item-layout{
            width: 45%;
        }
    }
    @media screen and (max-width: 1500px) and (min-width: 1024px){
        .tj-unit-item-layout{
            width: 48%;
        }
    }
    @media screen and (max-width: 1550px) and (min-width: 1500px){
        .tj-unit-item-layout{
            width: 33%;
        }
    }
    .tj-unit-item {
        margin-bottom: 30px;
        cursor: pointer;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        position: relative;
        background-color: white;
    }
    .tj-unit-item .unit-item-pic-wrapper {
        position: relative;
    }
    .tj-unit-item .unit-item-content {
        position: relative;
        min-height: 139px;
        padding: 20px 5px 0;
        background-color: #fff;
    }
    .pic-swiper {
        width: 100%;
        height: 265px;
        min-height: 200px;
        border-radius: 10px;
    }
    .swiper-container {
        margin: 0 auto;
        position: relative;
        overflow: hidden;
        list-style: none;
        padding: 0;
        z-index: 1;
    }
    .tj-unit-item .unit-item-content .unit-title {
        font-size: 28px;
        line-height: 33px;
        font-weight: 700;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        margin: 0;
        text-align: left;
    }
    .tj-unit-item .unit-item-content .unit-title a,.tj-unit-item .unit-item-content .unit-title a:visited {
        color: #333;
        text-decoration: none;
        font-size: 28px;
    }
    .tj-unit-item .unit-item-content .unit-desc-list {
        display: flex;
        overflow: hidden;
        align-items: center;
        margin: 10px 0;
        line-height: 18px;
        color: #666;
        text-overflow: ellipsis;
        justify-content: flex-start;
        padding: 0;
        font-size: 18px;
    }
    .tj-unit-item .unit-item-content .unit-desc-list li {
        display: inline;
        padding: 0;
        margin: 0;
    }
    .tj-unit-item .unit-item-content .unit-desc-list-extra {
        height: 20px;
        overflow: hidden;
        font-size: 15px;
    }
    .tj-unit-item .unit-item-content .unit-little-label-list {
        display: flex;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        -ms-flex-wrap: wrap;
        flex-wrap: wrap;
        margin: 0;
        padding: 0;
    }
    .unit-item-content .unit-little-label-list .unit-little-label-list-item {
        line-height: 16px;
        height: 18px;
        margin: 0 5px 3px 0;
        border-radius: 2px;
        white-space: nowrap;
        padding: 0 5px;
        color: rgb(255, 150, 69);
        background: rgb(255, 245, 224);
        border: 0px solid rgb(255, 176, 0);
    }
    .tj-unit-item .unit-item-content .unit-price-content {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: end;
        -ms-flex-align: end;
        align-items: flex-end;
        margin-top: 8px;
        height: 36px;
        overflow: hidden;
    }
    .tj-unit-item .unit-item-content .unit-price-content .unit-price {
        font-weight: 700;
        font-size: 20px;
        color: #fd8238;
        white-space: nowrap;
        margin: 0 10px 0 0;
        padding: 0;
    }
    .tj-unit-item .unit-item-content .unit-price-content .unit-price .text {
        line-height: 35px;
        font-size: 30px;
    }
    .tj-unit-item .unit-item-content .landlord-avatar {
        position: absolute;
        right: 0;
        bottom: 0;
        z-index: 1;
        width: 64px;
        height: 64px;
        border-radius: 50%;
        display: block;
        margin: 0;
        padding: 0;
        cursor: pointer;
    }
    .tj-avatar-layout .avatar-img {
        display: block;
        width: 100%;
        height: 100%;
        border-radius: 50%;
        border: 0;
        vertical-align: middle;
    }
    /*爱心图标，精灵图片*/
    .tj-unit-item .collection-icon {
        position: absolute;
        top: 12px;
        right: 12px;
        z-index: 1;
        cursor: pointer;
    }
    .icon-no-collection{
        background-image: url(../../assets/images/component-unitItem.12d52104.png);
        background-position: 0 0;
        width: 52px;
        height: 48px;
    }
    .icon-collection{
        background-image: url(../../assets/images/component-unitItem.12d52104.png);
        background-position: -56px 0;
        width: 50px;
        height: 45px;
    }
</style>
