<!--房源管理-->
<template>
    <el-row style="height: 100%;background-color: white;padding-top: 20px" v-loading="loading">
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
                        <el-select v-model="ruleForm.cityCode" filterable placeholder="请选择">
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
                    <el-form-item label="房源状态：">
                        <el-select size="small" v-model="ruleForm.state" placeholder="请选择">
                            <el-option label="待审核" value="0"></el-option>
                            <el-option label="已上架" value="1"></el-option>
                            <el-option label="审核失败" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="4" style="display: flex;margin-top: 3px;padding-left: 10px;">
                    <el-button size="small" type="primary" @click="submitForm('ruleForm')" style="min-width: 80px"><i class="el-icon-search"></i> 查询</el-button>
                    <el-button size="small"  @click="resetForm('ruleForm')" style="min-width: 80px"><i class="el-icon-refresh"></i> 重置</el-button>
                </el-col>
            </el-row>
        </el-form>
        <el-row style="display: flex;padding-left: 15px;top: -15px;justify-content: flex-start">
            <el-button size="small" plain type="primary"  style="min-width: 80px" @click="handleAddHouse()"><i class="el-icon-plus"></i>新增</el-button>
        </el-row>
        <!--表格-->
        <el-row style="height: 550px;text-align: center;overflow: auto">
            <el-table
                :data="tableData"
                stripe
                :header-cell-style="{'background-color': '#F9F9F9','border-bottom': '1px RGBA(199, 199, 199, 1) solid','text-align':'center'}">
                <el-table-column width="400" align="center" label="房源名称">
                    <template slot-scope="scope">
                        <div class="item-house-info">
                            <div class="info-left">
                                <img :src="scope.row.images[0]" alt="">
                                <div>房源编号.{{scope.row.id}}</div>
                            </div>
                            <div class="info-right">
                                <div class="title"><span>{{scope.row.name}}</span></div>
                                <div class="desc">{{scope.row.houseType}}居{{scope.row.bedNumber}}床{{scope.row.peopleNumber}}人 </div>
                            </div>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="location" label="房源位置"></el-table-column>
                <el-table-column width="200" align="center" label="出租时段">
                    <template slot-scope="scope">
                        <span>{{ scope.row.startDate+" - "+scope.row.endDate }}</span>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="房源状态">
                    <template slot-scope="scope">
                        <el-tag type="info" v-if="scope.row.state==0">待审核</el-tag>
                        <el-tag type="success" v-if="scope.row.state==1">已上架</el-tag>
                        <el-tag type="danger" v-if="scope.row.state==2">审核失败</el-tag>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="housePrice" label="房源价格"></el-table-column>
                <el-table-column align="center" prop="score" label="综合评分"></el-table-column>
                <el-table-column align="center" prop="createTime" label="创建日期"></el-table-column>
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" size="mini" v-if="scope.row.state==1" @click="handleDetail(scope.row)">查看</el-button>
                        <el-button type="text" size="mini" @click="handleUpdate(scope.row)">修改</el-button>
                        <el-popconfirm style="margin-left: 10px" title="确定删除吗？" @confirm="handleDelete(scope.row)">
                            <el-button slot="reference" type="text"><i class="el-icon-delete"></i>删除</el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <!--页码-->
        <el-row style="height: 50px;bottom: 0" class="row-page">
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
        <!--新增-->
        <el-dialog v-dialogDrag title="新增房源" :visible.sync="dialogAddHouse" :top="'10px'" :width="'1500px'">
            <HouseInfo :key="dialogAddHouse"  :house-id="houseId" :operation.sync="operator" :is-show.sync="dialogAddHouse"></HouseInfo>
        </el-dialog>
    </el-row>
</template>

<script>
    import house from "@/api/house";
    import HouseInfo from "@/components/landlord/HouseInfo";
    export default {
        components: {HouseInfo},
        data() {
            return {
                /*城市列表*/
                city:[],
                ruleForm: {
                    houseName: '',
                    cityCode: '',
                    state:'',
                    /*每页显示的内容个数*/
                    limit:10,
                    /*当前页数*/
                    page:1,
                },
                rules: {
                },
                /*房源数据*/
                tableData: [],
                /*总数*/
                total:0,
                /*新增房源*/
                dialogAddHouse:false,
                /*加载中*/
                loading:'',
                operator:'',
                /*房源id*/
                houseId:''
            }
        },
        created() {
        },
        mounted() {
            this.getCity();
            this.submitForm('ruleForm');
        },
        methods: {
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
                        house.landlordHouseList(this.ruleForm).then(res=>{
                            this.tableData=res.data.list;
                            this.total=res.data.total;
                            this.loading=false;
                        })
                    }
                })
            },
            /*重置查询条件*/
            resetForm(){
                this.ruleForm.houseName='';
                this.ruleForm.cityCode='';
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
            /*新增房源*/
            handleAddHouse(){
                this.operator='addHouse';
                this.dialogAddHouse=true;
            },
            /*修改房源*/
            handleUpdate(row){
                this.operator='updateHouse';
                this.houseId=row.id;
                this.dialogAddHouse=true;
            },
            /*删除房源*/
            handleDelete(row) {
                this.loading=true;
                house.deleteClientHouseById(row.id).then(res=>{
                    this.msgSuccess("删除成功！")
                    this.submitForm('ruleForm');
                }).catch(err=>{
                    this.loading=false;
                })
            },
            /*查看房源*/
            handleDetail(row){
                this.$router.push({
                    path:"/HouseDetail",
                    query:{
                        houseId:row.id,
                    }
                })
            }
        },
        watch:{
            /*监听弹窗关闭后刷新数据*/
            dialogAddHouse(){
                this.submitForm('ruleForm');
            }
        }
    }
</script>

<style scoped>
    .item-house-info{
        display: flex;
    }
    .item-house-info .info-left{
        margin-right: 10px;
    }
    .item-house-info .info-left img{
        width: 120px;
        height: 80px;
        cursor: pointer;
        -o-object-fit: cover;
        object-fit: cover;
        border-style: none;
    }
    .item-house-info .info-left div{
        line-height: 14px;
        font-size: 12px;
        color: #666;
        margin-top: 10px;
    }
    .info-right{
        width: 200px;
    }
    .title{
        text-align: left;
    }
    .item-house-info .info-right .title {
        line-height: 24px;
        min-height: 45px;
        font-size: 16px;
        color: #333;
        margin: 0;
        padding: 0;
    }
    .item-house-info .info-right .title span{
        width: -moz-fit-content;
        width: fit-content;
        max-width: 200px;
        cursor: pointer;
        overflow: hidden;
        -o-text-overflow: ellipsis;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
    .item-house-info .info-right .desc{
        margin-top: 14px;
        font-size: 14px;
        color: #666;
        text-align: left;
    }
</style>
