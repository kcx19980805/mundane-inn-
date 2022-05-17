<!--订单-->
<template>
    <el-row style="height: 100%;background-color: white;padding-top: 20px"  v-loading="loading">
        <!--筛选条件-->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="width: 100%;">
            <el-row>
                <el-col :span="4">
                        <el-form-item label="房源名称：" prop="houseName">
                            <el-input size="small" v-model="ruleForm.houseName" placeholder="请输入"></el-input>
                        </el-form-item>
                </el-col>
                <el-col :span="4">
                    <el-form-item label="城市名称：">
                        <el-select v-model="ruleForm.treeCode" filterable placeholder="请选择">
                            <el-option
                                v-for="item in city"
                                :key="item.treeCode"
                                :label="item.name"
                                :value="item.treeCode">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="4">
                    <el-form-item label="订单状态：">
                        <el-select size="small" v-model="ruleForm.state" placeholder="请选择">
                            <el-option label="待支付" value="0"></el-option>
                            <el-option label="待入住" value="1"></el-option>
                            <el-option label="已离店" value="2"></el-option>
                            <el-option label="已取消" value="3"></el-option>
                            <el-option label="已退款" value="4"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="4" style="display: flex;margin-top: 3px;padding-left: 10px;">
                    <el-button size="small" type="primary" @click="submitForm('ruleForm')" style="min-width: 80px"><i class="el-icon-search"></i> 查询</el-button>
                    <el-button size="small"  @click="resetForm('ruleForm')" style="min-width: 80px"><i class="el-icon-refresh"></i> 重置</el-button>
                </el-col>
            </el-row>
        </el-form>
        <!--表格-->
        <el-row style="height: 570px;text-align: center;overflow: auto">
            <el-table
                :data="tableData"
                stripe
                :header-cell-style="{'background-color': '#F9F9F9','border-bottom': '1px RGBA(199, 199, 199, 1) solid','text-align': 'center'}"
                >
                <el-table-column align="center" width="200" label="入住日期">
                    <template slot-scope="scope">
                        <span>{{ scope.row.startDate+" - "+scope.row.endDate }}</span>
                    </template>
                </el-table-column>
                <el-table-column align="center" width="300" prop="name" label="房源名称"></el-table-column>
                <el-table-column align="center" prop="location" label="房源位置"></el-table-column>
                <el-table-column align="center" prop="landlordName" label="房东姓名"></el-table-column>
                <el-table-column align="center" prop="landlordPhone" label="房东电话"></el-table-column>
                <el-table-column align="center" prop="amountActual" label="支付金额"></el-table-column>
                <el-table-column align="center" label="状态">
                    <template slot-scope="scope">
                        <el-tag type="warning" v-if="scope.row.state=='0'">待支付<i class="el-icon-time"></i>{{scope.row.timeLeft.min +":"+scope.row.timeLeft.second}}</el-tag>
                        <el-tag type="success" v-if="scope.row.state=='1'">待入住</el-tag>
                        <el-tag type="info" v-if="scope.row.state=='2'">已离店</el-tag>
                        <el-tag type="info" v-if="scope.row.state=='3'">已取消</el-tag>
                        <el-tag type="info" v-if="scope.row.state=='4'">已退款</el-tag>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="createTime" label="下单日期"></el-table-column>
                <el-table-column align="center" width="200" label="操作" >
                    <template slot-scope="scope">
                        <el-button type="text" size="mini" @click="handleDetail(scope.row)">查看</el-button>
                        <el-button type="text" size="mini" v-if="scope.row.state=='0'" @click="handlePay(scope.row)"><i class="el-icon-coin"></i>支付</el-button>
                        <el-button type="text" size="mini" v-if="scope.row.state=='1'" @click="handleCancel(scope.row)"><i class="el-icon-close"></i>取消</el-button>
                        <el-button type="text" size="mini" v-if="scope.row.state!='0' && scope.row.state!='1'" @click="handleDelete(scope.row)"><i class="el-icon-delete"></i>删除</el-button>
                        <el-button type="text" size="mini" v-if="scope.row.state=='2'" @click="handleComment(scope.row)">评论</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <!--页码-->
        <el-row style="height: 50px;bottom: 0">
            <el-pagination
                style="right: 0;position: absolute;padding-top: 15px"
                @size-change="handleSizeChange"
                @current-change="pageChange"
                :current-page="ruleForm.page"
                :page-sizes="[10, 20, 30, 50]"
                :page-size="ruleForm.limit"
                :total="total"
                layout="total, sizes, prev, pager, next, jumper"
                background >
            </el-pagination>
        </el-row>
        <!--评论-->
        <el-dialog v-dialogDrag :title="'评论'" :visible.sync="dialogComment" :width="'500px'" class="dialog">
            <div style="width:100%;">
                <p>{{currentTitle}}</p>
                <div style="text-align: left;margin-bottom: 10px">评论星级:</div>
                <el-rate v-model="score" style="float: left;margin-bottom: 10px"></el-rate>
                <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="content"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                 <el-button @click="dialogComment=false">取 消</el-button>
                 <el-button type="primary" @click="addComment()">确 定</el-button>
             </span>
        </el-dialog>
        <!--支付-->
        <el-dialog title="扫码支付" :visible.sync="dialogVisible" center :width="'300px'" v-loading="loadingResult">
            <div style="width: 100%;display: flex;justify-content: center;flex-wrap: wrap">
                <!-- 生成二维码图片 -->
                <vueQr :text="text" :size="200" v-if="!paySucc"></vueQr>
                <el-button type="primary" style="width: 200px" size="medium" @click="getOrderResult()" v-if="!paySucc">确认已支付</el-button>
                <el-result icon="success" title="支付成功,3s后返回" v-if="paySucc">
                    <template slot="extra">
                        <el-button type="primary" size="medium" @click="back()">返回</el-button>
                    </template>
                </el-result>
            </div>
        </el-dialog>
    </el-row>
</template>

<script>
    import order from "@/api/order";
    import vueQr from 'vue-qr';
    import comment from "@/api/comment";
    export default {
        props:[],
        components: {vueQr},
        data() {
            return {
                /*城市列表*/
                city:[],
                /*筛选条件*/
                ruleForm: {
                    houseName: '',
                    treeCode: '',
                    state:'',
                    /*每页显示的内容个数*/
                    limit:10,
                    /*当前页数*/
                    page:1,
                },
                rules: {
                    houseName: [
                        {required: false, message: '请输名称', trigger: 'blur'},
                    ],
                },
                /*表格加载中*/
                loading:false,
                /*表格数据*/
                tableData: [],
                /*评论弹窗*/
                dialogComment: false,
                /*评论房源的商家号*/
                currentUsername:'',
                /*评论房源的标题*/
                currentTitle:'',
                /*评论房源的id*/
                currentHouseId:'',
                /*评论订单的id*/
                currentOrderId:'',
                /*评分*/
                score:0,
                /*评论内容*/
                content:'',
                /*订单总数*/
                total:0,
                /*计时器列表*/
                interval:'',
                /*显示支付二维码弹窗*/
                dialogVisible: false,
                /*二维码内容*/
                text: "",
                /*支付成功*/
                paySucc: false,
                /*订单号*/
                out_trade_no:'',
                /*加载查询支付结果*/
                loadingResult:false,
            }
        },
        created() {

        },
        mounted() {
            this.getCity();
            this.submitForm('ruleForm');
        },
        destroyed(){
            window.clearInterval(this.interval);
        },
        methods: {
            /*更新倒计时*/
            updateTime(){
                window.clearInterval(this.interval);
                this.interval=window.setInterval(() => {
                    for (let i = 0; i < this.tableData.length; i++) {
                        //如果未支付，显示倒计时
                        if(this.tableData[i].state=="0"){
                            this.tableData[i].timeLeft=this.common.countDown(this.tableData[i].createTime,20)
                        }
                    }
                    this.loading=false;
                },1000);
            },
            /*查询城市*/
            async getCity() {
                //如果已经加载过不加载
                if(this.$store.getters.allCity.length==0){
                    await this.$store.dispatch('getAllCity')
                }
                this.city=this.$store.getters.allCity[0].list;
            },
            /*查询订单*/
            submitForm(formName) {
                this.loading=true;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        order.clientUserOrderList(this.ruleForm).then(res=>{
                            this.tableData=res.data.list;
                            this.total=res.data.total;
                            this.updateTime();
                        })
                    }
                })
            },
            /*重置查询条件*/
            resetForm(){
                this.ruleForm.houseName='';
                this.ruleForm.treeCode='';
                this.ruleForm.state='';
                this.submitForm('ruleForm');
            },
            /*改变每页显示数量*/
            handleSizeChange(val) {
                this.ruleForm.limit=val;
                this.submitForm('ruleForm');
            },
            /*换页*/
            pageChange(Page){
                this.ruleForm.page=Page;
                this.submitForm('ruleForm');
            },
            /*取消订单*/
            handleCancel(row) {
                this.loading=true;
                order.userCancelOrder(row.id).then(res=>{
                    this.msgSuccess("取消成功！")
                    this.submitForm('ruleForm');
                })
            },
            /*删除订单*/
            handleDelete(row) {
                this.loading=true;
                order.userDeleteOrder(row.id).then(res=>{
                    this.msgSuccess("删除成功！")
                    this.submitForm('ruleForm');
                })
            },
            /*支付订单*/
            handlePay(row){
                const _this=this;
                this.loading=true;
                this.paySucc=false;
                this.text='';
                this.out_trade_no='';
                order.payAliOrder(row.id).then(res=>{
                    this.loading=false;
                    this.text = res.data.qrCode;
                    this.out_trade_no = res.data.out_trade_no;
                    this.dialogVisible = true;
                    //使用websocket监控是否扫描，扫描成功显示成功并退出界面
                    if ("WebSocket" in window) {
                        let isHttps = 'https:' == document.location.protocol ? true : false;
                        let serviceIp = '127.0.0.1:8888';
                        let path="/socket/pay?userId="+this.$store.getters.userId;
                        let ws;
                        if('WebSocket' in window) {
                            ws = isHttps?new WebSocket("wss://"+serviceIp+path):new WebSocket("ws://"+serviceIp+path);
                        } else if('MozWebSocket' in window) {
                            ws = isHttps?new MozWebSocket("wss://"+serviceIp+path):new MozWebSocket("ws://"+serviceIp+path);
                        } else {
                            this.$message.error('您的浏览器不支持WebSocket!');
                            return false;
                        }
                        ws.onopen = function() {
                            console.log("WebSocket连接成功")
                        };
                        ws.onerror = ()=>{
                            _this.msgError('WebSocket连接错误!');
                        }
                        ws.onmessage = function(msg) {
                            if (msg.data == "TRADE_SUCCESS") {
                                _this.loadingResult=false;
                                _this.paySucc = true;
                                setTimeout(() => {
                                    _this.back()
                                }, 3000);
                            }else{
                                _this.msgError('支付失败!');
                                _this.back()
                            }
                            ws.close();
                        };
                        ws.onclose = function() {
                            console.log("WebSocket连接关闭");
                        };
                    } else {
                        _this.msgError('您的浏览器不支持 WebSocket!');
                    }
                }).catch((err) => {
                    this.back()
                })
            },
            /*返回*/
            back(){
                this.loadingResult=false;
                this.dialogVisible = false;
                this.loading=false;
                this.submitForm('ruleForm');
            },
            /*手动获取支付结果*/
            getOrderResult(){
                this.loadingResult=true;
                if(!this.paySucc){
                    order.getAliOrderResult(this.out_trade_no);
                }
            },
            /*打开评论*/
            handleComment(row){
                this.content='';
                this.score=0;
                this.dialogComment = true;
                this.currentTitle = row.name;
                this.currentHouseId = row.houseId;
                this.currentOrderId = row.id;
            },
            /*查看*/
            handleDetail(row){
                this.$router.push({
                    path:"/HouseDetail",
                    query:{
                        houseId:row.houseId,
                    }
                })
            },
            /*添加评论*/
            addComment(){
                if(this.content==''){
                    this.msgError('请输入评论内容');
                    return false
                }
                comment.saveHouseComment(this.currentOrderId,this.currentHouseId,this.content,this.score).then(res=>{
                    this.msgSuccess("评论成功");
                    this.dialogComment = false;
                })
            }
        }
    }
</script>

<style scoped>
    .dialog>>>.el-dialog__body {
        padding: 0px 20px;
        color: #606266;
        font-size: 14px;
        word-break: break-all;
    }
</style>
