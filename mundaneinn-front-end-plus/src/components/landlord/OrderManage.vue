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
                :header-cell-style="{'background-color': '#F9F9F9','border-bottom': '1px RGBA(199, 199, 199, 1) solid','text-align': 'center'}">
                <el-table-column align="center" width="200" label="入住日期">
                    <template slot-scope="scope">
                        <span>{{ scope.row.startDate+" - "+scope.row.endDate }}</span>
                    </template>
                </el-table-column>
                <el-table-column align="center" width="300" prop="name" label="房源名称"></el-table-column>
                <el-table-column align="center" prop="location" label="房源位置"></el-table-column>
                <el-table-column align="center" prop="userName" label="客户姓名"></el-table-column>
                <el-table-column align="center" prop="userPhone" label="客户电话"></el-table-column>
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
                        <el-button type="text" size="mini" v-if="scope.row.state=='1'" @click="handleCancel(scope.row)"><i class="el-icon-close"></i>取消</el-button>
                        <el-button type="text" size="mini" v-if="scope.row.state!='0' && scope.row.state!='1'" @click="handleDelete(scope.row)"><i class="el-icon-delete"></i>删除</el-button>
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
    </el-row>
</template>

<script>
import order from "@/api/order";
export default {
    props:[],
    components: {},
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
            },
            /*表格加载中*/
            loading:false,
            /*表格数据*/
            tableData: [],
            /*订单总数*/
            total:0,
            /*计时器列表*/
            interval:'',
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
                    order.landlordOrderList(this.ruleForm).then(res=>{
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
            order.landlordCancelOrder(row.id).then(res=>{
                this.msgSuccess("取消成功！")
                this.submitForm('ruleForm');
            })
        },
        /*删除订单*/
        handleDelete(row) {
            this.loading=true;
            order.landlordDeleteOrder(row.id).then(res=>{
                this.msgSuccess("删除成功！")
                this.submitForm('ruleForm');
            })
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
