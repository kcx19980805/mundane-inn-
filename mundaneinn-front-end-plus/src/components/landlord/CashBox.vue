<template>
    <el-row style="height: 100%;background-color: white;text-align: left;font-size: 14px">
        <el-row>
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <span>钱箱</span>
                </div>
                <div class="text item">
                    <div class="cash-box-info-panel">
                        <div class="info-panel-left">
                            <div>账户余额</div>
                            <p class="amount">￥{{balance}}</p>
                            <el-button class="ivn-btn ivu-btn-primary" type="info" plain @click="withdraw()">提现</el-button>
<!--                            <button class="ivn-btn ivu-btn-primary"><span>提现</span></button>-->
                        </div>
                        <div class="info-panel-right">
                            <div class="info-panel-right-item border-left-green">
                              <p>本月已入账</p>
                              <p class="amount">￥{{money.beenCredited}}</p>
                            </div>
                            <div class="info-panel-right-item border-left-yellow">
                                <p>本月待入账</p>
                                <p class="amount">￥{{money.pending}}</p>
                            </div>
                            <div class="info-panel-right-item border-left-blue">
                              <p>本月房费</p>
                              <p class="amount">￥{{parseInt(money.beenCredited)+parseInt(money.pending)}}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </el-card>
        </el-row>
        <!--表格-->
        <el-row>
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <span>收支明细</span>
                    <el-row style="width: 100%;height: 40px;">
                        <el-form ref="ruleForm" label-width="100px" class="demo-ruleForm" style="width: 100%;">
                            <el-col :span="4">
                                <el-form-item label="房源名称：">
                                    <el-input size="small" v-model="houseName" placeholder="请输入"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="操作：">
                                    <el-select size="small" v-model="operator" placeholder="请选择">
                                        <el-option label="增加" value="+"></el-option>
                                        <el-option label="减少" value="-"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4" style="display: flex;margin-top: 5px;padding-left: 10px;">
                                <el-button size="small" type="primary" @click="clientUserAmountRecordList()" style="min-width: 80px"><i class="el-icon-search"></i> 查询</el-button>
                                <el-button size="small"  @click="resetForm()" style="min-width: 80px"><i class="el-icon-refresh"></i> 重置</el-button>
                            </el-col>
                        </el-form>
                    </el-row>
                </div>
                <div class="text item" style="height: 370px;overflow: auto">
                    <el-table
                        :data="tableData"
                        border
                        :summary-method="getSummaries"
                        show-summary
                        stripe
                        :header-cell-style="{'background-color': '#F9F9F9','border-bottom': '1px RGBA(199, 199, 199, 1) solid','text-align': 'center'}">
                        <el-table-column prop="houseName" label="房源名称"></el-table-column>
                        <el-table-column align="center" width="200" label="出租时段">
                            <template slot-scope="scope">
                                <span>{{ scope.row.startDate+" - "+scope.row.endDate }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column align="center" prop="userName" label="客户名称"></el-table-column>
                        <el-table-column align="center" prop="userPhone" label="客户电话"></el-table-column>
                        <el-table-column align="center" prop="createTime" label="支付时间" ></el-table-column>
                        <el-table-column align="center" prop="operator" label="操作" ></el-table-column>
                        <el-table-column align="center" prop="amount" label="支付金额"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button
                                    size="mini"
                                    type="danger"
                                    @click="deleteAmountRecord(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <div class="text item">
                    <el-row style="height: 50px;bottom: 0">
                        <el-pagination
                            style="right: 0;position: absolute;padding-top: 15px"
                            @size-change="handleSizeChange"
                            @current-change="pageChange"
                            :current-page="page"
                            :page-sizes="[10, 20, 30, 50]"
                            :page-size="limit"
                            :total="total"
                            layout="total, sizes, prev, pager, next, jumper"
                            background >
                        </el-pagination>
                    </el-row>
                </div>
            </el-card>
        </el-row>
        <el-dialog
            title="提现申请"
            :visible.sync="dialogVisible"
            width="30%"
            >
          <el-input v-model="amount" placeholder="请输入额度"></el-input>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirm()">确 定</el-button>
          </span>
        </el-dialog>
    </el-row>
</template>

<script>
    import order from "@/api/order";
    import amountRecord from "@/api/amountRecord";
    import user from "@/api/user";
    import validate from "@/utils/validate";
    export default {
        data() {
            return {
                /*表格数据*/
                tableData: [],
                /*当前余额*/
                balance:'',
                /*房费余额信息*/
                money:[],
                /*每页显示的内容个数*/
                limit:10,
                /*总房屋个数*/
                total:0,
                /*当前页数*/
                page:1,
                /*房源名称*/
                houseName:"",
                /*操作符*/
                operator:"",
                /*提现弹窗*/
                dialogVisible:false,
                /*提现金额*/
                amount:'',
            };
        },
        created() {
            this.getUserInfo();
            this.getMoney();
            this.clientUserAmountRecordList()
        },
        methods: {
            /*查询用户信息*/
            getUserInfo(){
                user.getUserInfo().then(res=>{
                    this.balance=res.data.balance;
                }).catch(err=>{
                })
            },
            /*钱箱信息*/
            getMoney(){
                order.getMoney().then(res=>{
                    this.money=res.data
                })
            },
            /*用户余额变更记录列表*/
            clientUserAmountRecordList(){
                amountRecord.clientUserAmountRecordList(this.page,this.limit,this.houseName,this.operator).then(res=>{
                    this.tableData=res.data.list;
                    this.total=res.data.total;
                })
            },
            /*重置*/
            resetForm(){
                this.houseName='';
                this.operator='';
                this.clientUserAmountRecordList();
            },
            /*删除用户余额变更记录*/
            deleteAmountRecord(row){
                amountRecord.deleteAmountRecord(row.id).then(res=>{
                    this.msgSuccess("删除成功");
                    this.clientUserAmountRecordList();
                })
            },
            /*改变每页显示数量*/
            handleSizeChange(val) {
                this.limit=val;
                this.clientUserAmountRecordList();
            },
            /*换页*/
            pageChange(Page){
                this.page=Page
                this.clientUserAmountRecordList();
            },
            getSummaries(param) {
                const { columns, data } = param;
                const sums = [];
                columns.forEach((column, index) => {
                    if (index === 0) {
                        sums[index] = '合计';
                        return;
                    }
                    if (index === 3) {
                        sums[index] = '';
                        return;
                    }
                    const values = data.map(item => Number(item[column.property]));
                    if (!values.every(value => isNaN(value))) {
                        sums[index] = values.reduce((prev, curr) => {
                            const value = Number(curr);
                            if (!isNaN(value)) {
                                return prev + curr;
                            } else {
                                return prev;
                            }
                        }, 0);
                        sums[index] += ' 元';
                    } else {
                        sums[index] = '';
                    }
                });
                return sums;
            },
            /*提现*/
            withdraw(){
                this.amount='';
                this.dialogVisible=true;
            },
            /*确定提现*/
            confirm(){
                if(!validate.validMoney(this.amount )){
                    this.msgError("请输入正确的金额")
                }
                else if(Number(this.amount)>Number(this.balance)){
                    this.msgError("提现额度超过余额")
                }else{
                    this.msgSuccess("提现申请已发出")
                    //后端接口。。。
                }
                this.dialogVisible = false
            }
        }
    };
</script>

<style scoped>
    .g-cash-box-index-layout{
        width: 960px;
        min-height: 630px;
        padding: 0 20px;
        border-radius: 4px;
        box-shadow: 0 0 0 1px rgba(63,63,68,.05), 0 1px 3px 0 rgba(63,63,68,.15);
        font-size: 14px;
        font-family: MicrosoftYaHei;
        background: #fff;
        margin-left: 130px;
        color: #333;
        height: auto;
    }
    .cash-box-info-panel{
        display: flex;
    }
    .info-panel-left{
        width: 250px;
        border-right: 1px solid #eee;
    }
    .info-panel-left .amount{
        margin: 10px 0;
        line-height: 28px;
        font-size: 24px;
        color: #ff9645;
    }
    .ivn-btn{
        display: inline-block;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        vertical-align: middle;
        outline: 0;
        touch-action: manipulation;
        background-image: none;
        border: 1px solid rgba(0,0,0,0);
        white-space: nowrap;
        user-select: none;
        padding: 6px 15px;
        font-size: 12px;
        border-radius: 4px;
        transition: color .2s linear,background-color .2s linear,border .2s linear,box-shadow .2s linear,-webkit-box-shadow .2s linear;
    }
    .ivu-btn-primary{
        color: #bbbec4;
        background-color: #f7f7f7;
        border-color: #dddee1;
        width: 100px;
    }
    .info-panel-right{
        display: flex;
        align-items: center;
        padding-left: 40px;
    }
    .border-left-blue{
        border-left: 2px solid rgba(110,128,227,.5);
    }
    .info-panel-right-item{
        width: 223px;
        padding-left: 20px;
    }
    .info-panel-right-item .amount{
        margin: 10px 0;
        line-height: 24px;
        font-size: 18px;
    }
    .border-left-yellow {
        border-left: 2px solid rgba(253,179,107,.5);
    }
    .border-left-green{
        border-left: 2px solid limegreen;
    }
</style>
