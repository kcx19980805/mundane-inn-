<template>
    <!--大搜索框-->
    <div class="hc-home-search-wrapper animate__animated " :class="{'animate__bounceIn':!hideAnimate}" :style="searchStyle">
        <!--选择城市-->
        <section class="city-search-wrapper">
            <div class="city-wrap" @click="toggleCityEvent()">
                {{$store.state.search.cityName}}<span :class="{'el-icon-caret-bottom':!showCity,'el-icon-caret-top':showCity}"></span>
            </div>
            <!--城市下拉框-->
            <section class="search-city-panel-wrap" v-show="showCity">
                <div class="city-search-input">
                    <el-autocomplete
                        class="inline-input"
                        :fetch-suggestions="getSuggestedCity"
                        placeholder="请输入城市名称"
                        :trigger-on-focus="false"
                        v-model="suggestedCityName"
                        style="width: 100%"
                        prefix-icon="el-icon-search"
                        @select="selectSuggestedCity"></el-autocomplete>
                </div>
                <section class="hc-search-group-tabs">
                    <ul class="search-group-tab-list">
                        <li class="search-group-tab-list-item" :class="{active:cityOrder== 0 }" @click="toggleCityOrder(0)">全部城市</li>
                        <li class="search-group-tab-list-item" :class="{active:cityOrder== 1 }" @click="toggleCityOrder(1)">按字母排序</li>
                    </ul>
                    <div class="group-list" v-loading="loadingCity">
                        <div v-for="(item,key) in currentCityList"  style="text-align: left;width: 100%">
                            <ul>
                                <li><p>{{item.pingYin}}</p></li>
                            </ul>
                            <ul  >
                                <li class="search-group-tab-list-item group-list-item" v-for="(cityItem,cityKey) in item.list"
                                    @click="selectSuggestedCity(cityItem)" :key="cityKey">{{ cityItem.name }}
                                </li>
                            </ul>
                        </div>
                    </div>
                </section>
            </section>
        </section>
        <div class="divider"></div>
        <!--选择具体位置-->
        <section class="keyword-search-wrapper">
            <section  @click="toggleRegionEvent()">
                <el-input class="keyword-search-input" v-model="$store.state.search.region" placeholder="位置|位置关键字|民宿名"
                          clearable  @blur="regionBlur()" @focus="regionFocus()" @input="regionInput()" ref="region"></el-input>
            </section>
            <!--具体位置下拉框-->
            <section class="hc-search-keyword-panel" v-show="showRegion"  v-loading="loadingRegion" @mousedown.prevent="regionMousedown()">
                <section class="hc-search-group-tabs">
                    <ul class="search-group-tab-list">
                        <li class="search-group-tab-list-item" v-for="(item,index) in areaType" :class="{ active:regionTypeIndex == item.id}"
                            @click="getRegion(item.id)">{{item.typeName}}
                        </li>
                    </ul>
                    <ul class="group-list">
                        <li class="group-item-list-item" v-for="(item,key) in regionList"
                            @click="selectRegion(item)">
                            <a class="group-item-name">{{ item.name }}</a>
                        </li>
                    </ul>
                </section>
            </section>
        </section>
        <div class="divider"></div>
        <!--选择日期-->
        <section class="date-search-wrapper">
            <div class="block">
                <el-date-picker style=""
                                v-model="$store.state.search.dateRange"
                                value-format="yyyy-MM-dd"
                                type="daterange"
                                range-separator="-"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                :picker-options="pickerOptions"
                >
                </el-date-picker>
            </div>
        </section>
        <el-button class="search" @click="searchHandle()">搜索</el-button>
    </div>
</template>

<script>
import search from "@/api/search";
export default {
    name: "Search",
    props: ['hideAnimate', 'searchStyle'],
    data() {
        return {
            /*城市排序方式*/
            cityOrder:'0',
            /*当前城市列表*/
            currentCityList:[],
            /*显示加载城市界面*/
            loadingCity: false,
            /*显示城市下拉框*/
            showCity: false,
            /*建议的城市名称*/
            suggestedCityName: '',
            /*当前地区列表*/
            regionList: [],
            /*显示加载地区界面*/
            loadingRegion:false,
            /*显示地区分类下拉框*/
            showRegion: false,
            /*选择的地区分类索引*/
            regionTypeIndex: '',
            /*地区分类*/
            areaType:[],
            /*限制只能选择今天及以后的时间*/
            pickerOptions: {
                disabledDate(time) {
                    return time.getTime() < Date.now() - 8.64e7;
                }
            },
        }
    },
    beforeCreate(){
    },
    created() {
        this.getCity(0);
        this.getAreaType();
    },
    methods: {
        /*切换选择城市下拉框*/
        toggleCityEvent() {
            this.showCity = !this.showCity
            this.showRegion = false;
        },
        /*根据用户输入查询建议城市*/
        getSuggestedCity(queryString, cb) {
            let restaurants = this.$store.getters.allCity[0].list;
            let results = queryString ? restaurants.filter(this.cityFilter(queryString)) : restaurants;
            results.forEach(res=>{
                    res.value=res.name
                }
            )
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        /*城市查询过滤器*/
        cityFilter(queryString) {
            return (restaurant) => {
                return (restaurant.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            }
        },
        /*选择城市*/
        selectSuggestedCity(item) {
            this.$store.state.search.cityName = item.name;
            this.$store.state.search.cityCode = item.treeCode;
            this.showCity = false;
            this.getRegion(this.regionTypeIndex,true)
        },
        /*按下地区下拉框*/
        regionMousedown(){
            //阻止默认事件，这里阻止出发搜索框失去焦点事件导致下拉框消失
            //event.preventDefault()
        },
        /*地区获得焦点*/
        regionFocus(){
            this.showRegion=true;
        },
        /*地区失去焦点*/
        regionBlur(){
            this.showRegion=false;
        },
        /*输入地区清空经纬度*/
        regionInput(){
            this.$store.state.search.point.lng="";
            this.$store.state.search.point.lat="";
        },
        /*切换选择地区下拉框*/
        toggleRegionEvent() {
            this.showRegion = true
            this.showCity = false
        },
        /*查询城市*/
        async getCity(orderByPingYin) {
            this.cityOrder=orderByPingYin;
            //如果已经加载过不加载
            if(this.$store.getters.allCity.length==0){
                this.loadingCity=true;
                await this.$store.dispatch('getAllCity')
                await this.$store.dispatch('getOrderAllCity')
                this.loadingCity = false;
            }
            this.currentCityList=this.$store.getters.allCity;
        },
        /*切换城市排序方式*/
        toggleCityOrder(cityOrder){
            this.cityOrder=cityOrder;
            if(cityOrder==0){
                this.currentCityList=this.$store.getters.allCity;
            }else{
                this.currentCityList=this.$store.getters.orderAllCity;
            }
        },
        /*查询地区分类*/
        getAreaType(){
            this.loadingRegion=true;
            search.getClientAreaTypeList().then((res)=>{
              this.areaType=res.data;
              this.getRegion(this.areaType[0].id);
            })
        },
        /*查询地区*/
        getRegion(areaTypeId,regionIsEmpty) {
            this.loadingRegion=true;
            if(regionIsEmpty==true){
                this.$store.state.search.region="";
            }
            this.regionTypeIndex=areaTypeId;
            search.getClientAreaPopularList(areaTypeId,this.$store.getters.cityCode).then(res=>{
                this.regionList=res.data;
                this.loadingRegion=false;
            })
        },
        /*选择地区*/
        selectRegion(item){
            this.showRegion=false;
            this.$store.state.search.region=item.name;
            this.$store.state.search.point.lng=item.pointLng;
            this.$store.state.search.point.lat=item.pointLat;
        },
        /*调用父组件事件处理搜索*/
        searchHandle() {
            this.$emit('search')
        }
    }
}
</script>

<style scoped>
/*大搜索框*/
.hc-home-search-wrapper {
    position: relative;
    bottom: 144px;
    margin: 0 auto;
    z-index: 99;
    height: 64px;
    width: 950px;
    display: flex;
    align-items: center;
    border-radius: 50px;
    background: #fff;
}

/*城市查询*/
.city-search-wrapper {
    width: 158px;
    height: 34px;
    white-space: nowrap;
    text-align: center;
    display: block;
    margin: 0;
    padding: 0;
}

.city-wrap {
    font-size: 18px;
    line-height: 34px;
    color: #333;
    padding: 0 10px;
    position: relative;
    cursor: pointer;
    user-select: none;
    display: inline-block;
    max-width: 130px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    background-color: white;
}

/*查询面板*/
.search-city-panel-wrap, .hc-search-keyword-panel {
    position: absolute;
    min-height: 152px;
    background: #fff;
    top: 115%;
    left: 0;
    width: 100%;
    box-shadow: 0 8px 20px 0 rgba(0, 0, 0, .1);
    border-radius: 12px;
}

.search-city-panel-wrap {
    padding: 12px;
}

.hc-search-group-tabs {
    margin-top: 16px;
    padding: 0 15px;

}

.search-group-tab-list {
    width: 100%;
    height: 35px;
    background-color: white;
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: flex-start;
}

.search-group-tab-list-item {
    float: left;
    cursor: pointer;
    padding: 0 11px;
    position: relative;
    width: 84px;
    height: 34px;
    text-align: center;
    line-height: 34px;
    box-shadow: 0 2px 10px 0 rgba(0, 0, 0, .08);
    border-radius: 6px;
    margin-right: 8px;
}

.group-list-item {
    margin-top: 5px;
}

.group-list-item:hover {
    box-shadow: 0 2px 8px 0 rgba(255, 118, 50, .18);
}

/*分隔线*/
.divider {
    display: block;
    width: 1px;
    height: 30px;
    background: #ddd;
}

/*关键字/位置/房屋查询*/
.keyword-search-wrapper {
    margin: 0 10px;
    height: 34px;
}

.keyword-search-input {
    margin-top: -3px;
    font-size: 18px;
}

/* 利用穿透，设置input边框隐藏 */
.keyword-search-wrapper >>> .el-input__inner {
    border: 0;
}

.active {
    color: #ff9645;
    font-weight: 600;
    box-shadow: 0 2px 8px 0 rgba(255, 118, 50, .18);
}

.group-list {
    margin: 0;
    padding: 3px;
    margin: 10px 0;
    max-height: 280px;
    min-height: 60px;
    overflow-y: auto;
    position: relative;
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
}

.group-item-list-item {
    margin-bottom: 10px;
    display: inline-block;
    min-width: 84px;
    text-align: center;
    background: #f4f5f7;
    border-radius: 6px;
    line-height: 34px;
    font-size: 14px;
    color: #666;
    padding: 0 7px;
    margin-right: 8px;
    max-width: 100%;
    height: 100%;
}

.group-item-name {
    display: block;
    width: 100%;
}

/*日期选择*/
.date-search-wrapper {
    margin-left: 5px;
    margin-right: 10px;
    position: static;
    width: 380px;
}

/*搜索按钮*/
.search {
    height: 50px;
    line-height: 50px;
    font-size: 20px;
    color: #fff;
    background-image: linear-gradient(90deg, #ff7632, #ffa819);
    -webkit-box-shadow: 0 20px 22px -13px rgba(229, 100, 0, .2);
    box-shadow: 0 20px 22px -13px rgba(229, 100, 0, .2);
    border-radius: 25px;
    padding: 0 45px;
    margin-right: 5px;
    border: 0;
}

.search:hover {
    color: #fff;;
    background-image: linear-gradient(90deg, #ff7632, #ffa819);
}
.search:focus{
    color: #fff !important;
}
</style>
