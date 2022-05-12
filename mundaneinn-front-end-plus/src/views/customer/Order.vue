<template>
    <div>
        <!--顶部导航-->
        <Header></Header>
        <div class="order-fill-page" v-loading="loading">
            <div class="order-main">
                <div class="orderinfo-cont">
                    <div class="col-header">
                        <span class="col-title">预订信息</span>
                    </div>
                    <div class="orderinfo-main">
                        <el-form  label-width="80px">
                            <el-form-item label="入住时段" style="margin-bottom: 0">
                                <span>{{chooseDate[0]}} 至 {{chooseDate[1]}}</span>
                            </el-form-item>
                            <el-form-item label="支付金额" style="margin-bottom: 0">
                                <span>{{totalPrice}}（元）</span>
                            </el-form-item>
                            <el-form-item label="民宿名称" style="margin-bottom: 0">
                                <span>{{currentHouseData.name}}</span>
                            </el-form-item>
                            <el-form-item label="商家姓名" style="margin-bottom: 0">
                                <span>{{currentHouseData.userName}}</span>
                            </el-form-item>
                            <el-form-item label="商家电话" style="margin-bottom: 0">
                                <span>{{currentHouseData.phone}}</span>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="col-header">
                        <span class="col-title">入住人信息</span>
                    </div>
                    <div class="orderinfo-main">
                        <el-form  label-width="80px">
                            <el-form-item label="姓名" style="margin-bottom: 0">
                                <span>{{userInfo.name}}</span>
                            </el-form-item>
                            <el-form-item label="性别" style="margin-bottom: 0">
                                <span>{{userInfo.sex=="0"?'男':'女'}}</span>
                            </el-form-item>
                            <el-form-item label="身份证号" style="margin-bottom: 0">
                                <span>{{userInfo.idCard}}</span>
                            </el-form-item>
                            <el-form-item label="联系电话" style="margin-bottom: 0">
                                <span>{{userInfo.phone}}</span>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="submit-order-btn" @click="placeOrder()" v-loading="loadingPay">提交订单</div>
                </div>
            </div>
        </div>
        <el-dialog title="扫码支付" :visible.sync="dialogVisible" center :width="'300px'"  v-loading="loadingResult">
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
        <Footer></Footer>
    </div>
</template>

<script>
    import Footer from "@/components/Footer";
    import Header from "@/components/Header";
    import user from "@/api/user";
    import order from "@/api/order";
    import vueQr from 'vue-qr';
    export default {
        components: {Footer,Header,vueQr},
        props:["chooseDate","houseData","totalPrice"],
        data(){
            return{
                /*加载中*/
                loading:false,
                /*当前用户信息*/
                userInfo:'',
                /*当前房源信息*/
                currentHouseData:[],
                /*显示支付二维码弹窗*/
                dialogVisible: false,
                /*二维码内容*/
                text: "",
                /*支付成功*/
                paySucc: false,
                /*加载支付*/
                loadingPay:false,
                /*订单号*/
                out_trade_no:'',
                /*加载查询支付结果*/
                loadingResult:false,
            }
        },
        created() {
            this.currentHouseData=JSON.parse(this.houseData)
            this.getUserInfo();
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
            /*提交订单*/
            placeOrder(){
                if(this.loadingPay){
                    return false
                }
                const _this=this;
                this.loadingPay=true;
                this.paySucc=false;
                this.text='';
                this.out_trade_no='';
                order.getAliQrCode(this.$store.getters.userId,this.currentHouseData.id,this.chooseDate[0],this.chooseDate[1],this.totalPrice).then(res=>{
                  this.loadingPay=false;
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
                this.loadingPay=false;
                this.$router.go(-1);
            },
            /*手动获取支付结果*/
            getOrderResult(){
                this.loadingResult=true;
                if(!this.paySucc){
                    order.getAliOrderResult(this.out_trade_no);
                }
            },
        }
    }
</script>

<style scoped>
    .order-fill-page {
        margin: 100px auto;
        width: 1200px;
    }
    .order-main {
        margin: 15px auto;
        padding: 40px 30px;
        width: 800px;
        box-shadow: 0 0 3px 0 #ccc;
        box-sizing: inherit;
    }
    /*预定信息*/
    .orderinfo-cont {
        margin-bottom: 36px;
    }
    .col-header {
        width: 800px;
        padding: 8px 0;
        border-bottom: 2px solid #ddd;
        display: flex;
        justify-content: flex-start;
    }
    .col-title {
        padding-left: 8px;
        border-left: 3px solid #fd8238;
        font-size: 18px;
        font-weight: 700;
        color: #333;
    }
    .orderinfo-main {
        margin-top: 8px;
        font-size: 14px;
        color: #333;
    }
    .submit-order-btn{
        margin: 18px auto 0;
        width: 124px;
        height: 36px;
        line-height: 36px;
        font-size: 16px;
        border-radius: 2px;
        cursor: pointer;
        background-color: #fd8238;
        color: #fff;
        margin-top: 56px;
    }
</style>
