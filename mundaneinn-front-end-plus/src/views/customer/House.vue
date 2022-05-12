<template>
    <article style="width: 100%;overflow: hidden" >
        <div class="loading"  v-loading="loading" v-show="loading"></div>
        <nav>
          <Header></Header>
          <Search :hide-animate="true" :search-style="'bottom: -5px;width: 900px;height: 60px;margin-left: 200px;background: transparent;'" @search="searchHouseByAllFilters()"></Search>
        </nav>
        <section class="g-unitList-select-layout">
            <section class="selectRow">
                <!--人数-->
                <section class="g-unitList-selectUI-layout">
                    <section  class="selectUI">
                        <span class="title">人数</span>
                        <div class="el-select selectBox">
                            <el-dropdown trigger="click" @command="choosePeopleNumber">
                                <section @click="showPeopleNumber=!showPeopleNumber">
                                    <el-input  v-model="peopleNumber" placeholder="请选择" :suffix-icon="showPeopleNumber?
                                    'el-icon-caret-top':'el-icon-caret-bottom'" :readonly=true ></el-input>
                                </section>
                                <el-dropdown-menu slot="dropdown"  style="min-width: 219px;max-height: 200px;overflow: scroll">
                                    <el-dropdown-item :command="item+'人'" v-for="(item,index) in 10">{{item==10?'10人+':item+'人'}}</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </section>
                </section>
                <!--床数-->
                <section class="g-unitList-selectUI-layout">
                    <section  class="selectUI">
                        <span class="title">床数</span>
                        <div class="el-select selectBox">
                            <el-dropdown trigger="click" @command="chooseBedNumber">
                                <section @click="showBedNumber=!showBedNumber">
                                    <el-input  v-model="bedNumber" placeholder="请选择" :suffix-icon="showBedNumber?
                                    'el-icon-caret-top':'el-icon-caret-bottom'" :readonly=true></el-input>
                                </section>
                                <el-dropdown-menu slot="dropdown" style="min-width: 219px;max-height: 200px;overflow: scroll">
                                    <el-dropdown-item :command="item+'床'" v-for="(item,index) in 10">{{item==10?'10床+':item+'床'}}</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </section>
                </section>
                <!--位置-->
                <section class="g-unitList-select-location-layout">
                    <section class="locationUI">
                        <span class="title">位置</span>
                        <el-dropdown @command="chooseLocation" v-for="(item,index) in areaType" >
                            <span class="locationItem el-dropdown-link"> {{item.typeName}}<i class="el-icon-caret-bottom"></i></span>
                            <el-dropdown-menu slot="dropdown" class="suggest">
                                <ul class="suggest-contain" v-loading="loadingRegion" style="min-height: 30px;">
                                    <li v-for="(regionItem,key) in regionList[index].region"><el-dropdown-item  :command="regionItem" >{{regionItem.name}}</el-dropdown-item></li>
                                </ul>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </section>
                </section>
            </section>
            <section class="selectRow">
                <!--户型-->
                <section class="g-unitList-radioUI-layout">
                    <section class="radioUI">
                        <span class="title">户型</span>
                        <el-radio-group v-model="houseType"  @change="changeHouseType()">
                            <el-radio :label="1">一居</el-radio>
                            <el-radio :label="2">二居</el-radio>
                            <el-radio :label="3">三居</el-radio>
                            <el-radio :label="4">四居及以上</el-radio>
                        </el-radio-group>
                    </section>
                </section>
                <!--房价-->
                <section class="g-unitList-select-housePrice-layout">
                    <section class="housePriceUI">
                        <span class="title">房价</span>
                        <el-progress :percentage="housePrice" :color="customColor" class="progress" :format="format"></el-progress>
                        <span class="title" style="font-size: 14px;margin-left: -2px">元/晚</span>
                        <div class="progress-controller"  @mouseleave="changeHousePrice()">
                            <el-button-group>
                                <el-button icon="el-icon-minus" @click="cutHousePrice"></el-button>
                                <el-button icon="el-icon-plus" @click="addHousePrice" ></el-button>
                            </el-button-group>
                        </div>
                    </section>
                </section>
            </section>
            <section class="selectRow">
                <!--出租类型-->
                <section class="g-unitList-radioUI-layout" style="width: 20%">
                    <section class="radioUI">
                        <span class="title">出租类型</span>
                        <el-radio-group v-model="rentalType"  @change="changeRentalType()">
                            <el-radio :label="1">整套</el-radio>
                            <el-radio :label="0">单间</el-radio>
                        </el-radio-group>
                    </section>
                </section>
                <!--配套-->
                <section class="g-unitList-selectUI-layout">
                    <section  class="selectUI">
                        <span class="title">配套</span>
                        <div class="el-select selectBox">
                            <el-dropdown trigger="click" @command="">
                                <section @click="showMatching=!showMatching">
                                    <el-input  v-model="matchNumber" placeholder="请选择" :suffix-icon="!showMatching?
                                    'el-icon-caret-bottom':'el-icon-caret-top'" :readonly=true></el-input>
                                </section>
                                <el-dropdown-menu slot="dropdown">
                                    <div class="matching">
                                        <el-checkbox-group v-model="matchList"  size="medium">
                                            <el-checkbox v-for="(item,index) in matchingList" :label="item.id" class="matching-checkbox">{{item.name}}</el-checkbox>
                                        </el-checkbox-group>
                                        <div class="matching-options">
                                            <el-dropdown-item command="close" class="dropdown-item">
                                                <el-button size="mini" @click="matchingOptions('clear')">清除条件</el-button>
                                            </el-dropdown-item>
                                            <el-dropdown-item command="close" class="dropdown-item">
                                                <el-button size="mini" style="width: 100px" @click="matchingOptions('confirm')">
                                                   确定
                                                </el-button>
                                            </el-dropdown-item>
                                        </div>
                                    </div>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </section>
                </section>
                <!--房型-->
                <section class="g-unitList-selectUI-layout">
                    <section  class="selectUI">
                        <span class="title">房型</span>
                        <div class="el-select selectBox">
                            <el-dropdown trigger="click" @command="">
                                <section @click="showHouse=!showHouse">
                                    <el-input  v-model="houseNumber" placeholder="请选择" :suffix-icon="!showHouse?
                                    'el-icon-caret-bottom':'el-icon-caret-top'" :readonly=true></el-input>
                                </section>
                                <el-dropdown-menu slot="dropdown">
                                    <div class="matching">
                                        <el-checkbox-group v-model="residenceList"  size="medium">
                                            <el-checkbox v-for="(item,index) in houseList" :label="item.id" class="matching-checkbox">{{item.name}}</el-checkbox>
                                        </el-checkbox-group>
                                        <div class="matching-options">
                                            <el-dropdown-item command="close" class="dropdown-item">
                                                <el-button size="mini" @click="houseOptions('clear')">清除条件</el-button>
                                            </el-dropdown-item>
                                            <el-dropdown-item command="close" class="dropdown-item">
                                                <el-button size="mini" style="width: 100px" @click="houseOptions('confirm')">
                                                    确定
                                                </el-button>
                                            </el-dropdown-item>
                                        </div>
                                    </div>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </section>
                </section>
                <!--推荐-->
                <section class="g-unitList-selectUI-layout" style="width:5%;min-width: 95px">
                    <section  class="selectUI">
                        <el-button type="text" @click="getRecommendHouseData()">猜你喜欢</el-button>
                    </section>
                </section>
            </section>
        </section>
        <section id="fixedHeaderRoot" class="g-unitList-selected-layout" :class="isFixedTop?'g-unitList-selected-isFixed':''">
            <section class="selectedBox">
                <el-tag v-show="$store.getters.region!=''" closable @close="$store.state.search.region='',this.$store.state.search.point.lng='',this.$store.state.search.point.lat='',searchHouseByAllFilters()">
                    {{$store.getters.region}}
                </el-tag>
                <el-tag v-show="$store.getters.dateRange!=''" closable @close="$store.state.search.dateRange='',searchHouseByAllFilters()">
                    {{$store.getters.dateRange[0]+" - "+$store.getters.dateRange[1]}}
                </el-tag>
                <el-tag v-show="peopleNumber!=''" closable @close="peopleNumber='',searchHouseByAllFilters()">
                    {{peopleNumber}}
                </el-tag>
                <el-tag v-show="bedNumber!=''" closable @close="bedNumber='',searchHouseByAllFilters()">
                    {{bedNumber}}
                </el-tag>
                <el-tag v-show="houseType!=''" closable @close="houseType='',searchHouseByAllFilters()">
                    {{houseType+'居'}}
                </el-tag>
                <el-tag v-show="housePrice!=0">
                    {{realHousePrice+'元以内'}}
                </el-tag>
                <el-tag v-show="rentalType!==''" closable @close="rentalType=='',searchHouseByAllFilters()">
                    {{rentalType=='0'?'单间':'整套'}}
                </el-tag>
                <el-tag v-for="(item,index) in matchList" closable @close="removeType(matchList,item),searchHouseByAllFilters()">
                    {{findTypeName(item,'matchList')}}
                </el-tag>
                <el-tag v-for="(item,index) in residenceList" closable @close="removeType(residenceList,item),searchHouseByAllFilters()">
                    {{findTypeName(item,'residenceList')}}
                </el-tag>
                <el-tag v-show="isRecommend==1" closable @close="isRecommend=0,searchHouseByAllFilters()">
                    猜你喜欢
                </el-tag>
                <el-button type="text" size="mini" @click="clearAllFilters()">清除条件</el-button>
            </section>
        </section>
        <!--房屋列表-->
        <section class="g-unitList-list-layout">
            <section>
                <!--排序-->
                <section class="sortBox">
                    <span class="anchor"><span class="houseNum">{{total}}套房屋符合条件</span></span>
                    <section class="sort">
                        <span :class="{'currentSortType':sortOrder=='price'}" @click="houseSort('price')">
                            价格
                            <span class="caret-wrapper">
                                <img src="../../assets/images/排序.png" v-show="sortImg==''">
                                <img src="../../assets/images/升序.png" v-show="sortImg=='upper'">
                                <img src="../../assets/images/降序.png" v-show="sortImg=='down'">
                            </span>
                        </span>
                        <span :class="{'currentSortType':sortOrder=='comment'}" @click="houseSort('comment')">好评优先</span>
                    </section>
                </section>
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
                                <i class="collection-icon" :class="collectionList.some(e=>e==item.id)?'icon-collection':'icon-no-collection'" @click="updateCollection(item.id)"></i>
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
                <!--页码条-->
                <div class="tj-pagination">
                    <el-pagination
                        style="right: 100px;position: absolute;"
                        @size-change="handleSizeChange"
                        @current-change="pageChange"
                        :current-page="currentPage"
                        :page-sizes="[6, 20, 30, 50]"
                        :page-size="pageSize"
                        :total="total"
                        layout="total, sizes, prev, pager, next, jumper"
                        background >
                    </el-pagination>
                </div>
            </section>
        </section>
        <Footer></Footer>
    </article>
</template>

<script>
    import Footer from "@/components/Footer";
    import Header from "@/components/Header";
    import Search from "@/components/Search";
    import search from "@/api/search";
    import house from "@/api/house";
    export default {
        components: {Footer, Header,Search},
        props:[],
        data(){
            return{
                /*用户选择的人数*/
                peopleNumber:'',
                /*显示人数选择下拉框*/
                showPeopleNumber:false,
                /*用户选择的床数*/
                bedNumber:'',
                /*显示床数下拉框*/
                showBedNumber:false,
                /*显示加载地区界面*/
                loadingRegion:false,
                /*地区分类*/
                areaType:[],
                /*地区列表*/
                regionList: [],
                /*用户选择的户型*/
                houseType:'',
                /*整个页面是否显示加载图标*/
                loading:false,
                /*房价进度条*/
                housePrice:40,
                /*用户选择的房价*/
                realHousePrice:'',
                /*进度条颜色*/
                customColor:'#ff9645',
                /*用户选择的出租类型*/
                rentalType:'',
                /*配套条件数量*/
                matchNumber:'',
                /*是否显示配套信息*/
                showMatching:false,
                /*配套数据*/
                matchingList:[],
                /*用户选择的配套*/
                matchList:[],
                /*房型条件数量*/
                houseNumber:'',
                /*是否显示房型信息*/
                showHouse:false,
                /*房型数据*/
                houseList:[],
                /*用户选择的房型*/
                residenceList:[],
                /*是否推荐，0否1是*/
                isRecommend:0,
                /*所有筛选条件*/
                allFilters:{
                  page:1,
                  limit:10,
                  cityName:'',
                  cityCode:'',
                  region:'',
                  pointLng:'',
                  pointLat:'',
                  startTime:'',
                  endTime:'',
                  peopleNumber:0,
                  bedNumber:0,
                  houseType:'',
                  housePrice:'',
                  rentalType:'',
                  matchList:[],
                  residenceList:[],
                  isRecommend:0,
                  orderByPriceAsc:0,
                  orderByPriceDesc:0,
                  orderByScore:0
                },
                /*是否固定在顶部*/
                isFixedTop:false,
                /*排序图标*/
                sortImg:'',
                /*排序方式*/
                sortOrder:'',
                /*是否按价格升序，0否1是*/
                orderByPriceAsc:0,
                /*是否按价格降序，0否1是*/
                orderByPriceDesc:0,
                /*是否按评分，0否1是*/
                orderByScore:0,
                /*房屋数据*/
                houseData: [],
                /*用户收藏房源列表*/
                collectionList:[],
                /*每页显示的内容数量*/
                pageSize:6,
                /*房源数量*/
                total:0,
                /*当前页*/
                currentPage:1,
            }
        },
        created() {
            //得到分类位置
            this.getAllAreaType();
            //得到配套
            this.getMatchList();
            //得到房型
            this.getResidenceList();
            //得到房源
            this.searchHouseByAllFilters();
        },
        mounted(){
            // 滚动条的获取
            window.addEventListener('scroll', this.handleScroll, true)
        },
        methods:{
            /*查询所有地区分类*/
            async getAllAreaType(){
                this.loadingRegion=true;
                let area;
                await search.getClientAreaTypeList().then((res)=>{
                    this.areaType=res.data;
                    area=res.data;
                    for (let i = 0; i < area.length; i++) {
                        this.regionList[i]={"areaTypeId":area[i].id,"region":[]}
                    }
                })
                await this.getAllRegion();
            },
            /*查询所有地区*/
            async getAllRegion() {
                this.loadingRegion=true;
                for (let i = 0; i < this.regionList.length; i++) {
                    await search.getClientAreaPopularList( this.regionList[i].areaTypeId,this.$store.getters.cityCode).then(res=>{
                        this.regionList[i].region=res.data;
                    })
                }
                this.loadingRegion=false;
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
            /*选择人数，查询结果*/
            choosePeopleNumber(number){
                this.peopleNumber=number;
                this.showPeopleNumber=false;
                this.searchHouseByAllFilters()
            },
            //选择床数，查询结果
            chooseBedNumber(number){
                this.bedNumber=number
                this.showBedNumber=false
                this.searchHouseByAllFilters()
            },
            //选择位置，查询结果
            chooseLocation(item){
                this.$store.state.search.region=item.name;
                this.$store.state.search.point.lng=item.pointLng;
                this.$store.state.search.point.lat=item.pointLat;
                this.searchHouseByAllFilters()
            },
            //选择户型，查询结果
            changeHouseType(value){
                this.searchHouseByAllFilters()
            },
            //增加房价
            addHousePrice() {
                this.housePrice += 5;
                if (this.housePrice > 100) {
                    this.housePrice = 100;
                }
            },
            //减少房价
            cutHousePrice() {
                this.housePrice -= 5;
                if (this.housePrice < 0) {
                    this.housePrice = 0;
                }
            },
            //格式化房价显示
            format(percentage){
                if(percentage<40){
                    this.realHousePrice =percentage*10;
                }
                else if(percentage<60){
                    this.realHousePrice =percentage*15;
                }
                else if(percentage<80){
                    this.realHousePrice =percentage*20;
                }
                else if(percentage<=85){
                    this.realHousePrice =percentage*40;
                }
                else if(percentage<=95){
                    this.realHousePrice =percentage*100;
                }
                else if(percentage<=100){
                    this.realHousePrice =percentage*300;
                }
                return this.realHousePrice;
            },
            /*选择房价，查询结果*/
            changeHousePrice(){
                this.searchHouseByAllFilters()
            },
            /*选择出租类型，查询结果*/
            changeRentalType(){
                this.searchHouseByAllFilters()
            },
            //选择配套，查询结果
            matchingOptions(option){
                if(option=='clear'){
                    this.matchList=[];
                    this.matchNumber='';
                } else if(option=='confirm'){
                    this.matchNumber=this.matchList.length+"项";
                }
                this.searchHouseByAllFilters();
            },
            /*选择房型，查询结果*/
            houseOptions(option){
                if(option=='clear'){
                    this.residenceList=[];
                    this.houseNumber='';
                }
                if(option=='confirm'){
                    this.houseNumber=this.residenceList.length+"项";
                }
                this.searchHouseByAllFilters();
            },
            /*猜你喜欢*/
            getRecommendHouseData(){
                this.isRecommend=1;
                this.searchHouseByAllFilters();
            },
            //根据所有筛选条件查询民宿结果
            searchHouseByAllFilters(){
                this.loading=true
                new Promise((resolve, reject) =>{
                    this.getCollection(resolve, reject);
                }).then(()=>{
                    this.getHouseData()
                })
            },
            /*查询房源*/
            getHouseData(){
                //得到所有筛选条件
                this.allFilters.page=this.currentPage;
                this.allFilters.limit=this.pageSize;
                this.allFilters.cityName=this.$store.getters.cityName;
                this.allFilters.cityCode=this.$store.getters.cityCode;
                this.allFilters.region=this.$store.getters.region;
                this.allFilters.pointLng=this.$store.getters.point.lng;
                this.allFilters.pointLat=this.$store.getters.point.lat;
                if(this.$store.getters.dateRange!=""){
                    this.allFilters.startTime=this.$store.getters.dateRange[0];
                    this.allFilters.endTime=this.$store.getters.dateRange[1];
                }else{
                    this.allFilters.startTime="";
                    this.allFilters.endTime="";
                }
                this.allFilters.peopleNumber=this.peopleNumber.replace("人","").replace("人+","")//人数，1...
                this.allFilters.bedNumber=this.bedNumber.replace("床","").replace("床+","")//床数，1...
                this.allFilters.houseType=this.houseType;//户型，一居...
                this.allFilters.housePrice=this.realHousePrice//房价，400...
                this.allFilters.rentalType=this.rentalType//出租类型，single（单间），complete（整套）...
                this.allFilters.matchList=this.matchList//配套
                this.allFilters.residenceList=this.residenceList//房型
                this.allFilters.isRecommend=this.isRecommend;
                this.allFilters.orderByPriceAsc=this.orderByPriceAsc;
                this.allFilters.orderByPriceDesc=this.orderByPriceDesc;
                this.allFilters.orderByScore=this.orderByScore;
                console.log("=====所有条件======")
                console.log(this.allFilters)
                //如果不是选择的地区而手动输入的地区，查询经纬度
                if((this.$store.getters.point.lat==""||this.$store.getters.point.lng=="")&&this.$store.getters.region.trim()!=""){
                    let _this=this;
                    // 坐标解析
                    let geoc = new BMap.Geocoder();
                    geoc.getPoint(this.$store.getters.region, function(point){
                        if (point) {
                            console.log("====用户输入地址解析======")
                            console.log(point)
                            _this.allFilters.pointLng=point.lng;
                            _this.allFilters.pointLat=point.lat;
                        }else{
                            _this.msgWarning("您输入的地址可能有误！");
                        }
                        house.getHouseList(_this.allFilters).then(res=>{
                            console.log("====解析地址房源======")
                            console.log(res)
                            _this.houseData=res.data.list;
                            _this.total=res.data.total;
                            _this.loading=false;
                        })
                    }, this.$store.getters.cityName);
                }else{
                    house.getHouseList(this.allFilters).then(res=>{
                        console.log("====查询房源======")
                        console.log(res)
                        this.houseData=res.data.list;
                        this.total=res.data.total;
                        this.loading=false;
                    })
                }
            },
            /*清除所有条件*/
            clearAllFilters(){
                this.$store.state.search.region="";
                this.$store.state.search.point.lng="";
                this.$store.state.search.point.lat="";
                this.$store.state.search.dateRange="";
                this.peopleNumber="";
                this.bedNumber="";
                this.houseType="";
                this.realHousePrice="";
                this.housePrice=20;
                this.rentalType="";
                this.matchNumber='';
                this.matchList=[];
                this.houseNumber='';
                this.residenceList=[];
                this.sortImg='';
                this.orderByPriceAsc=0;
                this.orderByPriceDesc=0;
                this.orderByScore=0;
                this.isRecommend=0;
                this.searchHouseByAllFilters()
            },
            //下拉滚动条时将筛选条件固定在顶部
            handleScroll(){
                if(window.pageYOffset>340){
                    this.isFixedTop=true
                }
                if(window.pageYOffset<340){
                    this.isFixedTop=false
                }
            },
            //房屋排序
            houseSort(type){
                this.sortOrder=type;
                if(type=='price'){
                    this.orderByScore=0;
                    //按价格排序
                    if(this.sortImg=='upper'){//降序
                        this.sortImg='down'
                        this.orderByPriceAsc=0;
                        this.orderByPriceDesc=1;
                    } else{//升序
                        this.sortImg='upper'
                        this.orderByPriceAsc=1;
                        this.orderByPriceDesc=0;
                    }
                } else if(type=='comment'){
                    this.sortImg='';
                    this.orderByPriceAsc=0;
                    this.orderByPriceDesc=0;
                    this.orderByScore=1;
                }
                this.searchHouseByAllFilters();
            },
            //得到收藏
            getCollection(resolve, reject){
                this.collectionList=[];
                house.getUserCollect().then(res=>{
                    for (let i = 0; i < res.data.length; i++) {
                        this.collectionList.push(res.data[i].id)
                    }
                    resolve()
                })
            },
            //取消/添加收藏
            updateCollection(houseId,e){
                e = e || window.event;
                //为了减少数据库开销，这里更新后不查询
                let isCollection = e.currentTarget.getAttribute("class").includes("icon-collection");
                if(isCollection){
                    e.currentTarget.setAttribute("class","collection-icon icon-no-collection");
                    //删除属性
                    house.deleteCollect(houseId).then(res=>{
                    })
                }else{
                    e.currentTarget.setAttribute("class","collection-icon icon-collection");
                    house.saveCollect(houseId).then(res=>{
                    })
                }
            },
            /*选择每页显示多少条*/
            handleSizeChange(val) {
                this.pageSize=val;
                this.searchHouseByAllFilters();
            },
            /*换页*/
            pageChange(Page){
                this.currentPage=Page
                this.searchHouseByAllFilters();
            },
            /*根据配套id或房型id查找对应的名称*/
            findTypeName(id,type){
                let list;
                if(type=="matchList"){
                    list=this.matchingList;
                }else if(type=="residenceList"){
                    list=this.houseList;
                }
                for (let i = 0; i < list.length; i++) {
                    if(list[i].id==id){
                        return list[i].name;
                    }
                }
            },
            /*移除配套或房型选项*/
            removeType(arr,val){
                this.common.removeByValue(arr,val);
                this.matchNumber=this.matchList.length+"项";
                this.houseNumber=this.residenceList.length+"项";
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
        },
        watch: {

        },
        computed:{
        }
    }
</script>

<style scoped>
    nav{
        width: 100%;
        height: 70px;
    }
    /*条件选择区域*/
    /*人数/床数*/
    .g-unitList-select-layout{
        width: 100%;
        padding: 20px;
        background: #f7f9fb;
        min-width: 1330px;
    }
    .g-unitList-select-layout .selectRow{
        display: flex;
        margin-bottom: 10px;
    }
    .g-unitList-selectUI-layout{
        background: #fff;
        -webkit-box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        margin-right: 10px;
        font-size: 16px;
        color: #333;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -ms-flex-wrap: nowrap;
        flex-wrap: nowrap;
        width: 20%;
    }
    .g-unitList-selectUI-layout .selectUI{
        padding: 10px 20px;
        width: 100%;
        position: relative;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
    }
    .g-unitList-selectUI-layout .title{
        line-height: 40px;
        white-space: nowrap;
    }
    .g-unitList-selectUI-layout .el-select {
        flex-grow: 1;
        display: inline-block;
        position: relative;

    }
    /* 利用穿透，设置input边框隐藏 */
    .el-select>>>.el-input__inner {
        border-style: none;
        font-size: 18px;
        text-align: center;
        padding: 0;
    }
    /*位置/热门推荐...*/
    .g-unitList-select-location-layout:last-child {
        margin-right: 0;
    }
    .g-unitList-select-location-layout {
        width: 56%;
        background: #fff;
        -webkit-box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        margin-right: 10px;
        font-size: 16px;
        color: #333;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -ms-flex-wrap: nowrap;
        flex-wrap: nowrap;
        -webkit-box-pack: start;
        -ms-flex-pack: start;
        justify-content: flex-start;
    }
    .g-unitList-select-location-layout .locationUI {
        padding: 20px;
    }
    .g-unitList-select-location-layout .locationUI .title {
        white-space: nowrap;
    }
    .g-unitList-select-location-layout .locationUI .locationItem {
        color: #666;
        margin-left: 20px;
        cursor: pointer;
    }
    /*选择位置，弹出的建议*/
    .suggest-contain{
        padding: 5px;
        width: 400px;
        max-height: 250px;
        overflow: auto;
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    /*户型*/
    .g-unitList-radioUI-layout{
        background: #fff;
        -webkit-box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        margin-right: 10px;
        font-size: 16px;
        color: #333;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        flex-wrap: nowrap;
        justify-content: flex-start;
        padding-right: 4px;
        box-sizing: content-box;
        width: 40.3%;
    }
    .g-unitList-radioUI-layout .radioUI {
        padding: 20px;
        width: 100%;
    }
    .g-unitList-radioUI-layout .radioUI .title{
        margin-right:10px;
    }
    /*房价*/
    .g-unitList-select-housePrice-layout{
        width: 56%;
        background: #fff;
        box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        font-size: 16px;
        color: #333;
        display: flex;
        -ms-flex-wrap: nowrap;
        flex-wrap: nowrap;
        -webkit-box-pack: start;
        -ms-flex-pack: start;
        justify-content: flex-start;
        position: relative;
        margin-right: 0;
    }
    .g-unitList-select-housePrice-layout .housePriceUI{
        padding: 11px;
        width: 100%;
        position: absolute;
        bottom: 0;

    }
    .g-unitList-select-housePrice-layout .housePriceUI .title {
        white-space: nowrap;
        float: left;
        margin-right: 10px;
        line-height: 40px;
    }
    .progress{
        width: 55%;
        line-height: 40px;
        float: left;
    }
    .progress-controller{
        float: left;
    }
    /*配套*/
    .matching{
        clear: both;
        max-width: 480px;
        padding-left: 25px;
    }
    .matching-checkbox{
        width: 85px;
        height: 25px;
        margin-top: 20px;
        margin-left: 5px;
    }
    .matching-options{
        padding-right: 30px;
        display: flex;
        justify-content: space-between;
    }
    .dropdown-item:hover{
        clear: both;
    }
    /*浮动显示条件区域*/
    .g-unitList-selected-layout{
        background: #fff;
        -webkit-box-shadow: 0 1px 0 0 rgba(0,0,0,.15);
        box-shadow: 0 1px 0 0 rgba(0,0,0,.15);
        padding: 10px 0;
        border-bottom: 1px solid #e9e9e9;
        display: flex;
        justify-content: flex-start;
    }
    .g-unitList-selected-layout .selectedBox{
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
        width: 80%;
        line-height: 30px;
        vertical-align: top;
        padding-left: 20px;
    }
    .g-unitList-selected-layout .selectedBox .el-tag{
        margin:5px 5px;
    }
    .g-unitList-selected-isFixed{
        position: fixed;
        width: 100%;
        top: 0;
        z-index: 99;
    }
    /*住宿内容列表区域*/
    .g-unitList-list-layout{
        width: 97.5%;
        padding: 0 20px;
        background-color: #fff;
    }
    /*加载图标*/
    .loading{
        position: fixed;
        width: 100vw;
        height: 100vh;
        z-index: 100;
        top:0%;
    }
    /*房屋排序*/
    .g-unitList-list-layout .sortBox{
        display: flex;
        justify-content: space-between;
        font-size: 18px;
        line-height: 26px;
        padding: 15px 0;
        width: 100%;
    }
    .g-unitList-list-layout .sortBox .anchor, .g-unitList-list-layout .sortBox .anchor a{
        color: #999;
    }
    .g-unitList-list-layout .sortBox .anchor .houseNum{
        padding-left: 10px;
    }
    .g-unitList-list-layout .sortBox .sort{
        float: right;
        display: inline-block;
        color: #666;
    }
    .g-unitList-list-layout .sortBox .sort .currentSortType{
        color: #ff9645;
    }
    .g-unitList-list-layout .sortBox .sort>span{
        margin-right: 36px;
        cursor: pointer;
    }
    .caret-wrapper{
        display: inline-flex;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        height: 26px;
        width: 4px;
        vertical-align: middle;
        cursor: pointer;
        overflow: initial;
        position: relative;
    }
    /*房屋内容项区域*/
    .g-unitList-list-layout .tj-unit-list{
        width: calc(100% + 20px);
        margin-left: -10px;
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    .tj-unit-item-layout{
        display: block;
        padding: 0 10px;
        width: 32%;
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
            width: 31.8%;
        }
    }

    .tj-unit-item {
        margin-bottom: 30px;
        cursor: pointer;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        position: relative;
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
        height: 296px;
        min-height: 296px;
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
        margin-right: 5px;
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
        background-image: url("../../assets/images/component-unitItem.12d52104.png");
        background-position: 0 0;
        width: 52px;
        height: 48px;
    }
    .icon-collection{
        background-image: url("../../assets/images/component-unitItem.12d52104.png");
        background-position: -56px 0;
        width: 50px;
        height: 45px;
    }
    /*分页条*/
    .g-unitList-list-layout .tj-pagination {
        text-align: center;
        padding: 30px 0 60px;
        margin: 0;
        white-space: nowrap;
        color: #303133;
        font-weight: 700;
    }
</style>
