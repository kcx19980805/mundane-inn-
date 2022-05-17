<!--添加/修改房源-->
<template>
    <el-row style="height: 100%;background-color: white;text-align: left" v-loading="loading">
        <el-row>
            <!--步骤条-->
            <div class="g-comStepNav-layout">
                <el-steps :active="active" finish-status="success" class="step-nav">
                    <el-step title="房源位置"></el-step>
                    <el-step title="房源概况"></el-step>
                    <el-step title="设施和服务"></el-step>
                    <el-step title="照片和描述"></el-step>
                    <el-step title="时段和价格"></el-step>
                </el-steps>
            </div>
        </el-row>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm" style="width: 100%;">
            <!--地图定位-->
            <el-row class="g-hcComModal-layout" v-show="active===0">
                <el-col :span="6" class="item-contain">
                    <div class="region-left">
                        <div class="title">你的房源在哪里？</div>
                        <el-form-item prop="city" >
                            <p>国家/城市：{{ruleForm.city}}</p>
                        </el-form-item>
                        <el-input placeholder="请输入位置" v-model="input" id="suggestId" style="min-width: 300px;width: 100%" @input="showSuggest()">
                            <template slot="prepend">中国大陆</template>
                        </el-input>
                        <h5>房源位置</h5>
                        <el-tag style="min-width: 300px;width: 100%">{{cityAndRegion}}</el-tag>
                        <p>经度</p>
                        <el-form-item prop="pointLng" >
                            <el-tag prop="pointLng"  style="min-width: 300px;width: 100%">{{ruleForm.pointLng}}</el-tag>
                        </el-form-item>
                        <p>纬度</p>
                        <el-form-item prop="pointLat" >
                            <el-tag style="min-width: 300px;width: 100%">{{ruleForm.pointLat}}</el-tag>
                        </el-form-item>
                        <p>详细位置</p>
                        <el-form-item prop="location" >
                            <el-input size="mini" placeholder="请输入具体门牌号" v-model="ruleForm.location" style="min-width: 300px;width: 100%"></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="16" class="item-contain">
                    <div class="region-right" id="map"></div>
                </el-col>
            </el-row>
            <!--出租类型，床数，人数，户型，标题-->
            <el-row class="g-hcComModal-layout" v-show="active===1">
                <el-row>
                    <el-col :span="4" class="item-contain">
                        <h2>宜住人数</h2>
                        <el-form-item prop="peopleNumber" >
                            <el-select v-model="ruleForm.peopleNumber" placeholder="请选择人数" size="small">
                                <el-option v-for="item in options" :key="item" :label="item==10?item+'人以上':item+'人'" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" class="item-contain">
                        <h2>床数</h2>
                        <el-form-item prop="bedNumber" >
                            <el-select v-model="ruleForm.bedNumber" placeholder="请选择床数" size="small">
                                <el-option v-for="item in options" :key="item" :label="item==10?item+'床以上':item+'床'" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" class="item-contain">
                        <h2>户型</h2>
                        <el-form-item prop="houseType" >
                            <el-select v-model="ruleForm.houseType" placeholder="请选择户型" size="small">
                                <el-option v-for="item in options" :key="item" :label="item==10?item+'户以上':item+'户'" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" class="item-contain">
                        <h2>客厅数量</h2>
                        <el-form-item prop="parlorNumber" >
                            <el-select v-model="ruleForm.parlorNumber" placeholder="请选择" size="small">
                                <el-option v-for="item in options" :key="item" :label="item+'厅'" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" class="item-contain">
                        <h2>卫生间数量</h2>
                        <el-form-item prop="bathroomNumber" >
                            <el-select v-model="ruleForm.bathroomNumber" placeholder="请选择" size="small">
                                <el-option v-for="item in options" :key="item" :label="item+'卫'" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="4" class="item-contain">
                      <h2>厨房数量</h2>
                        <el-form-item prop="kitchenNumber" >
                            <el-select v-model="ruleForm.kitchenNumber" placeholder="请选择" size="small">
                                <el-option v-for="item in options" :key="item" :label="item+'厨'" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" class="item-contain">
                        <h2>房型</h2>
                        <el-form-item prop="residenceType" >
                            <el-radio v-model="ruleForm.residenceType" label="0">小区住宅</el-radio>
                            <el-radio v-model="ruleForm.residenceType" label="1">公寓</el-radio>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" class="item-contain">
                        <h2>出租方式</h2>
                        <el-form-item prop="residenceType" >
                            <el-radio v-model="ruleForm.rentalType" label="1">整套</el-radio>
                            <el-radio v-model="ruleForm.rentalType" label="0">单间</el-radio>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" class="item-contain">
                        <h2>是否近地铁</h2>
                        <el-form-item prop="isNearSubway" >
                            <el-radio v-model="ruleForm.isNearSubway" label="0">是</el-radio>
                            <el-radio v-model="ruleForm.isNearSubway" label="1">否</el-radio>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8" class="item-contain">
                        <h2>房屋标题</h2>
                        <el-form-item prop="name" >
                            <el-input v-model="ruleForm.name" placeholder="请输入标题" size="small" maxlength="50" clearable></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-row>
            <!--配套列表，房型列表-->
            <el-row class="g-hcComModal-layout" v-show="active===2">
                <el-row>
                    <el-col :span="24" class="item-contain">
                        <h2>配套设施</h2>
                        <el-form-item prop="matchList" >
                            <el-checkbox-group v-model="matchList" class="item-checkList">
                                <el-checkbox v-for="(item,index) in matchingList" :label="item.id" class="item-check" :checked="ruleForm.matchList.some(e=>e==item.id)">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24" class="item-contain">
                        <h2>房型</h2>
                        <el-form-item prop="residenceList" >
                            <el-checkbox-group v-model="residenceList" class="item-checkList">
                                <el-checkbox v-for="(item,index) in houseList" :label="item.id" class="item-check" :checked="ruleForm.residenceList.some(e=>e==item.id)">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-row>
            <!--图片，描述-->
            <el-row class="g-hcComModal-layout" v-show="active===3">
                <el-row>
                    <div class="item-title-left">
                        <h2>
                            房屋照片 <span>{{ImageNumber}}/5</span>
                            <p style="position: relative; left: -6px;">【必填】 至少上传1张</p>
                        </h2>
                    </div>
                </el-row>
                <el-row>
                    <el-form-item prop="images" >
                        <div class="ivu-form-item-content">
                            <div class="photo-item">
                                <el-upload
                                    class="avatar-uploader"
                                    action=""
                                    :show-file-list="false"
                                    :on-success="handleAvatarSuccess1"
                                    :http-request="Upload"
                                    :before-upload="beforeAvatarUpload"
                                    :on-error="handleError">
                                    <img v-if="ruleForm.images[0]" :src="ruleForm.images[0]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                            <div class="photo-item">
                                <el-upload
                                    class="avatar-uploader"
                                    action=""
                                    :show-file-list="false"
                                    :on-success="handleAvatarSuccess2"
                                    :http-request="Upload"
                                    :before-upload="beforeAvatarUpload"
                                    :on-error="handleError">
                                    <img v-if="ruleForm.images[1]" :src="ruleForm.images[1]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                            <div class="photo-item">
                                <el-upload
                                    class="avatar-uploader"
                                    action=""
                                    :show-file-list="false"
                                    :on-success="handleAvatarSuccess3"
                                    :http-request="Upload"
                                    :before-upload="beforeAvatarUpload"
                                    :on-error="handleError">
                                    <img v-if="ruleForm.images[2]" :src="ruleForm.images[2]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                            <div class="photo-item">
                                <el-upload
                                    class="avatar-uploader"
                                    action=""
                                    :show-file-list="false"
                                    :on-success="handleAvatarSuccess4"
                                    :http-request="Upload"
                                    :before-upload="beforeAvatarUpload"
                                    :on-error="handleError">
                                    <img v-if="ruleForm.images[3]" :src="ruleForm.images[3]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                            <div class="photo-item">
                                <el-upload
                                    class="avatar-uploader"
                                    action=""
                                    :show-file-list="false"
                                    :on-success="handleAvatarSuccess5"
                                    :http-request="Upload"
                                    :before-upload="beforeAvatarUpload"
                                    :on-error="handleError">
                                    <img v-if="ruleForm.images[4]" :src="ruleForm.images[4]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                        </div>
                    </el-form-item>
                </el-row>
            </el-row>
            <!--价格，时段-->
            <el-row class="g-hcComModal-layout" v-show="active===4">
                <el-col :span="8" class="item-contain">
                    <el-row>
                        <h2>每日出租价格/晚</h2>
                        <el-form-item prop="housePrice" >
                            <el-input v-model="ruleForm.housePrice" placeholder="请输入价格" style="width: 150px" size="small" maxlength="6" clearable></el-input>
                        </el-form-item>
                    </el-row>
                    <el-row>
                        <h2>折扣</h2>
                        <el-form-item prop="discount" >
                            <el-select v-model="ruleForm.discount" placeholder="请选择折扣" size="small">
                                <el-option v-for="item in options" :key="item" :label="item+'折'" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-row>
                    <el-row>
                        <h2>出租时段</h2>
                        <el-form-item prop="chooseDate" >
                            <el-date-picker style=""
                                            v-model="ruleForm.chooseDate"
                                            value-format="yyyy-MM-dd"
                                            type="daterange"
                                            range-separator="-"
                                            start-placeholder="开始日期"
                                            end-placeholder="结束日期"
                                            :picker-options="pickerOptions"
                            >
                            </el-date-picker>
                        </el-form-item>
                    </el-row>
                </el-col>
                <el-col :span="12" class="item-contain">
                    <el-row>
                        <h2>描述您的房屋</h2>
                        <el-form-item prop="describe" >
                            <quill-editor ref="text" v-model="ruleForm.describe" class="myQuillEditor" :options="editorOption"
                                          @change="onEditorChange($event)" style="height: 380px"/>
                        </el-form-item>
                    </el-row>
                </el-col>
            </el-row>
        </el-form>
        <el-row class="footer">
            <el-col :span="4">
                <el-button @click="back()">返回</el-button>
            </el-col>
            <el-col :span="14">
            </el-col>
            <el-col :span="4" style="display: flex;justify-content: flex-end">
                <el-button  @click="prev" v-show="active!=0">上一步</el-button>
                <el-button  @click="next" v-show="active!=4">下一步</el-button>
                <el-button  @click="submitForm('ruleForm')" v-show="active===4&&operation=='addHouse'">添加房源</el-button>
                <el-button  @click="submitForm('ruleForm')" v-show="active===4&&operation=='updateHouse'">确认修改</el-button>
            </el-col>
        </el-row>
    </el-row>
</template>

<script>
    import { client } from '../../utils/alioss';
    import { quillEditor} from 'vue-quill-editor';
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    import validate from "@/utils/validate";
    import house from "@/api/house";

    export default {
        components:{quillEditor},
        /*操作：新增/修改，房源id，是否显示*/
        props:["operation","houseId","isShow"],
        data() {
            return {
                /*房源信息表单*/
                ruleForm: {
                    /*房源id*/
                    id:'',
                    /*房源图片*/
                    images:[],
                    /*城市*/
                    city:'',
                    /*房源名称*/
                    name:'',
                    /*详细位置*/
                    location:'',
                    /*房型（0小区住宅1公寓）*/
                    residenceType:'',
                    /*客厅数量*/
                    parlorNumber:'',
                    /*卫生间数量*/
                    bathroomNumber: '',
                    /*厨房数量*/
                    kitchenNumber:'',
                    /*人数1-10,10表示10人及以上*/
                    peopleNumber: '',
                    /*床数1-10,10表示10人及以上*/
                    bedNumber: '',
                    /*户型1-4,4表示4户及以上*/
                    houseType:'',
                    /*房源描述*/
                    describe:'',
                    /*配套id*/
                    matchList:[],
                    /*房型id*/
                    residenceList:[],
                    /*经度*/
                    pointLng:'',
                    /*纬度*/
                    pointLat: '',
                    /*房源价格*/
                    housePrice: '',
                    /*折扣*/
                    discount:'',
                    /*出租时段*/
                    chooseDate:'',
                    /*出租类型（0单间1整套）*/
                    rentalType:'',
                    /*是否近地铁0是1否*/
                    isNearSubway:'',
                    /*开始出租日期*/
                    startDate:'',
                    /*结束出租日期*/
                    endDate:''
                },
                rules: {
                    images:[
                        { required: true, message: '请选择房源照片', trigger: 'blur' }
                    ],
                    city:[
                        { required: true, message: '请选择城市', trigger: 'blur' }
                    ],
                    name: [
                        {required: true, message: '请输入房源名称', trigger: 'blur'},
                        {min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur'}
                    ],
                    location: [
                        {required: true, message: '请输入房源具体位置', trigger: 'blur'},
                    ],
                    residenceType: [
                        {required: true, message: '请选择房型', trigger: 'blur'},
                    ],
                    parlorNumber: [
                        {required: true, message: '请选择客厅数量', trigger: 'change'}
                    ],
                    bathroomNumber: [
                        {required: true, message: '请选择卫生间数量', trigger: 'change'}
                    ],
                    kitchenNumber: [
                        {required: true, message: '请选择厨房数量', trigger: 'change'}
                    ],
                    peopleNumber: [
                        {required: true, message: '请选择宜住人数', trigger: 'change'}
                    ],
                    bedNumber: [
                        {required: true, message: '请选择床数', trigger: 'change'}
                    ],
                    houseType: [
                        {required: true, message: '请选择户型', trigger: 'change'}
                    ],
                    describe: [
                        {required: true, message: '请输入房源描述', trigger: 'change'}
                    ],
                    matchList: [
                        {required: true, message: '请选择配套', trigger: 'change'}
                    ],
                    residenceList: [
                        {required: true, message: '请选择房型', trigger: 'change'}
                    ],
                    pointLng: [
                        {required: true, message: '请在地图上标记点位', trigger: 'change'}
                    ],
                    pointLat: [
                        {required: true, message: '请在地图上标记点位', trigger: 'change'}
                    ],
                    housePrice: [
                        {
                            validator:(rule, value, callback) => {
                                validate.validMoney(value)?callback(): callback(new Error("房价格式不正确"));
                            },
                            trigger: 'blur'
                        }
                    ],
                    discount: [
                        {required: true, message: '请选择折扣', trigger: 'change'}
                    ],
                    chooseDate: [
                        {required: true, message: '请选择出租时段', trigger: 'blur'}
                    ],
                    rentalType: [
                        {required: true, message: '请选择出租类型', trigger: 'change'}
                    ],
                    isNearSubway: [
                        {required: true, message: '请选择是否近地铁', trigger: 'change'}
                    ],
                },
                /*活动的步骤*/
                active: 0,
                input:'',
                /*当前地区*/
                cityAndRegion:"",
                /*选项*/
                options:[0,1,2,3,4,5,6,7,8,9,10],
                /*配套列表*/
                matchingList: [],
                matchList:[],
                /*房型列表*/
                houseList:[],
                residenceList:[],
                /*当前图片个数*/
                ImageNumber:0,
                /*限制只能选择今天及以后的时间*/
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() < Date.now();
                    }
                },
                /*房源数据*/
                houseData:[],
                /*富文本编辑器配置*/
                editorOption: {
                    modules: {
                        // 工具栏
                        toolbar: {
                            container:[
                                ['bold', 'italic', 'underline', 'strike'],    //加粗，斜体，下划线，删除线
                                ['blockquote', 'code-block'],     //引用，代码块
                                [{ 'header': 1 }, { 'header': 2 }],        // 标题，键值对的形式；1、2表示字体大小
                                [{ 'list': 'ordered'}, { 'list': 'bullet' }],     //列表
                                [{ 'script': 'sub'}, { 'script': 'super' }],   // 上下标
                                [{ 'indent': '-1'}, { 'indent': '+1' }],     // 缩进
                                [{ 'direction': 'rtl' }],             // 文本方向
                                [{ 'size': ['small', false, 'large', 'huge'] }], // 字体大小
                                [{ 'header': [1, 2, 3, 4, 5, 6, false] }],     //几级标题
                                [{ 'color': [] }, { 'background': [] }],     // 字体颜色，字体背景颜色
                                [{ 'font': [] }],     //字体
                                [{ 'align': [] }],    //对齐方式
                                ['clean'],    //清除字体样式
                               // ['image','video']    //上传图片、上传视频
                            ],
                        },
                    },
                    placeholder: '请输入正文...1000字以内'
                },
                loading:false,
            };
        },
        created() {
        },
        async mounted() {
            let _this=this
            // 百度地图API功能
            // 创建Map实例
            let map = new BMap.Map("map");
            // 开启滚轮缩放
            map.enableScrollWheelZoom();
            // 坐标解析，显示到前端
            let geoc = new BMap.Geocoder();
            function parsePoint(point) {
                _this.ruleForm.pointLng=point.lng
                _this.ruleForm.pointLat=point.lat
                geoc.getLocation(point, function (rs) {
                    let addComp = rs.addressComponents;
                    console.log("======解析坐标点得到的位置======")
                    console.log(addComp)
                    _this.ruleForm.city=addComp.city;
                    _this.cityAndRegion=_this.common.parseBaiDuMap(addComp);
                });
            }
            if(this.operation=="updateHouse"){
                await this.getHouseDetail();
                map.centerAndZoom(new BMap.Point(this.ruleForm.pointLng,this.ruleForm.pointLat),15);
                let opts = {
                    // 信息窗口宽度
                    width: 150,
                    title: "房源位置"
                }
                //创建信息窗口对象
                let infoWindow = new BMap.InfoWindow(this.ruleForm.location, opts);
                map.openInfoWindow(infoWindow, map.getCenter());
            }else{
                // 初始化地图,设置城市和地图级别。
                map.centerAndZoom("北京", 15);
                //通过浏览器定位当前位置
                let geolocation = new BMap.Geolocation();
                geolocation.getCurrentPosition(function (r) {
                    if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                        let mk = new BMap.Marker(r.point);
                        map.addOverlay(mk);
                        //地图移动到定位点
                        map.panTo(r.point);
                        console.log('您的定位位置：' + r.point.lng + ',' + r.point.lat);
                        parsePoint(r.point);
                    }
                    else {
                        console.log('定位错误，状态' + this.getStatus());
                    }
                });
                this.getMatchList();
                this.getResidenceList();
            }
            //自动列出输入地址的建议
            let ac = new BMap.Autocomplete({"input": "suggestId","location": map});
            //点击下拉列表的建议地址后
            let myValue;
            ac.addEventListener("onconfirm", function (e) {
                let _value = e.item.value;
                //省-市-县-街道-商店名
                myValue = _value.province +','+ _value.city + ','+_value.district +','+ _value.street +','+ _value.business;
                setPlace();
            });
            //重新绘制地图
            function setPlace() {
                //清除地图上所有覆盖物
                map.clearOverlays();
                function myFun() {
                    //获取第一个智能搜索的结果的坐标
                    let pp = local.getResults().getPoi(0).point;
                    console.log('选择下拉列表的位置：' + pp.lng + ',' + pp.lat);
                    parsePoint(pp);
                    //设置中心点
                    map.centerAndZoom(pp, 15);
                    //添加标注
                    map.addOverlay(new BMap.Marker(pp));
                }
                let local = new BMap.LocalSearch(map, { //智能搜索
                    onSearchComplete: myFun
                });
                local.search(myValue);
            }
            //点击地图位置显示省，市，区，街道，街道号,商业地址
            map.addEventListener("click", function (e) {
                console.log('地图点击的位置：' + e.point.lng + ',' + e.point.lat);
                map.centerAndZoom(e, 15);
                map.addOverlay(new BMap.Marker(e));
                parsePoint(e.point)
            });
        },
        methods: {
            /*房源详情*/
            async getHouseDetail() {
                this.loading=true;
                await house.getHouseInfo(this.houseId).then(res=>{
                    this.ruleForm=res.data;
                    this.ruleForm.chooseDate=[res.data.startDate,res.data.endDate];
                    this.cityAndRegion=this.ruleForm.location;
                    this.getMatchList();
                    this.getResidenceList();
                    this.loading=false
                }).catch(()=>{
                })
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
            /*弹出层显示百度地图建议*/
            showSuggest(){
                let obj=document.getElementsByClassName("tangram-suggestion-main")
                for (let i = 0; i < obj.length; i++) {
                    obj[i].style.setProperty('z-index', '9999', 'important');
                }
            },
            /*返回*/
            back(){
                this.$emit("update:isShow",false);
            },
            //上一步
            prev(){
                --this.active
                if (this.active < 0) this.active = 0;
            },
            //下一步
            next() {
                ++this.active
                if (this.active > 4) this.active = 4;
            },
            //图片上传之前
            beforeAvatarUpload(file) {
                const isPNG = file.type === 'image/png';
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isPNG && !isJPG) {
                    this.$message.error('上传图片只能是 PNG或JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传图片大小不能超过 2MB!');
                }
                return (isPNG||isJPG) && isLt2M;
            },
            /*上传图片*/
            async Upload(file) {
                let fileName = 'house' + `${Date.parse(new Date())}`+'.png';  //定义唯一的文件名
                let url;
                await client().multipartUpload(fileName, file.file,{headers: {
                        'Content-Disposition': 'inline',//设置头部，否则阿里oss的地址是下载而不是浏览图片
                        'Content-Type': 'png'//注意：根据图片或者文件的后缀来设置
                    }}).then(result => {
                    //得到上传后的文件地址
                    let index=result.res.requestUrls[0].indexOf('?')
                    if(index==-1){
                        url=result.res.requestUrls[0]
                    }
                    else{
                        url=result.res.requestUrls[0].substring(0,index)
                    }
                })
                return url
            },
            /*头像上传成功*/
            handleAvatarSuccess(res, file){
                this.ImageNumber++;//处理图片未刷新问题，不知道原因，可能是数据必须发送变化
                this.ImageNumber=this.ruleForm.images.length
                this.$message.success("上传成功!")
            },
            handleAvatarSuccess1(res, file) {
                this.ruleForm.images[0] = res;
                this.handleAvatarSuccess(res, file)
            },
            handleAvatarSuccess2(res, file) {
                this.ruleForm.images[1] = res;
                this.handleAvatarSuccess(res, file)
            },
            handleAvatarSuccess3(res, file) {
                this.ruleForm.images[2] = res;
                this.handleAvatarSuccess(res, file)
            },
            handleAvatarSuccess4(res, file) {
                this.ruleForm.images[3] = res;
                this.handleAvatarSuccess(res, file)
            },
            handleAvatarSuccess5(res, file) {
                this.ruleForm.images[4] = res;
                this.handleAvatarSuccess(res, file)
            },
            /*上传失败*/
            handleError() {
                this.$message("上传失败,请重新上传图片!");
            },
            /*限制内容在1000字*/
            onEditorChange(event){
                event.quill.deleteText(1000,4)
            },
            /*添加/修改房源*/
            submitForm(formName) {
                this.ruleForm.matchList=this.matchList;
                this.ruleForm.residenceList=this.residenceList;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.ruleForm.startDate=this.ruleForm.chooseDate[0];
                        this.ruleForm.endDate=this.ruleForm.chooseDate[1];
                        console.log(this.ruleForm)
                        if(this.operation=='addHouse') {
                            house.saveClientHouse(this.ruleForm).then(res=>{
                                this.msgSuccess("添加成功，等待管理员审核");
                                this.back();
                            }).catch(err=>{
                               // this.back();
                            })
                        }
                        else if(this.operation=='updateHouse'){
                            house.updateClientHouse(this.ruleForm).then(res=>{
                                this.msgSuccess("修改成功，等待管理员审核");
                                this.back();
                            }).catch(err=>{
                                //this.back();
                            })
                        }
                    }else{
                        this.msgError("信息有误，请检查")
                    }
                })
            },
        },
    }
</script>

<style scoped>
    .g-comStepNav-layout{
        box-shadow: 0 4px 48px 20px rgba(0,0,0,.06);
    }
    .step-nav{
        padding: 15px 20px 0 20px;
    }
    /*主要步骤区域*/
    .g-hcComModal-layout{
        padding: 20px;
        height: 550px;
        overflow-y:scroll;
        border-bottom: 1px solid #ccc;
    }
    .footer{
        padding:12px 20px 0 0;
        display: flex;
        justify-content: space-between;
    }
    /*位置*/
    .g-hcComModal-layout .title{
        font-size: 24px;
        font-weight: 700;
    }
    .region-left{
        min-width: 300px;
        padding: 5px;
    }
    .region-right{
        min-height: 480px;
        background-color: #ccc;
    }
    .g-hcComModal-layout h5{
        font-weight: 700;
        font-size: 16px;
        color: #333;
    }
    /*概况*/
    .item-contain{
        margin:10px;
    }
    .item-checkList{
        width: 40%;
        padding: 5px;
    }
    .item-check{
        width: 80px;
    }
    /*照片*/
    .item-title-left{
        float: left;
        height: 100%;
        display: flex;
        -webkit-box-align: center;
        align-items: center;
    }
    .item-title-left h2{
        font-weight: 700;
        font-size: 16px;
        color: #333;
        line-height: 21px;
    }
    .item-title-left h2 span{
        font-size: 12px;
        font-weight: 400;
        line-height: 16px;
        margin-left: 9px;
    }
    .item-title-left p{
        font-size: 12px;
        font-weight: 400;
        color: #999;
        line-height: 16px;
        margin-top: 4px;
        text-align: left;
    }
    .ivu-form-item-content {
        display: flex;
        flex-wrap: wrap;
    }
    .photo-item{
        border: 1px solid #ddd;
        margin: 0 10px 10px;
        width: 260px;
        height: 162px;
    }
    .photo-item i{
        line-height: 162px;
        height: 162px
    }

    /*时段和价格*/
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        width: 100%
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 256px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 260px;
        height: 162px;
        display: block;
    }

</style>
