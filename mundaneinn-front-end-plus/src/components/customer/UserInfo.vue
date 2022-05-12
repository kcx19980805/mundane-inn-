<!--用户信息-->
<template>
    <el-row style="height: 100%" v-loading="loading">
        <el-col :span="6">
            <el-card class="box-card user-info-card">
                <div slot="header" class="clearfix">
                    <span>个人信息</span>
                </div>
                <div class="text item">
                    <div class="head-img">
                        <el-upload
                            class="avatar-uploader"
                            action=""
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :http-request="Upload"
                            :before-upload="beforeAvatarUpload"
                            :on-error="handleError">
                            <img v-if="ruleForm.headImg" :src="ruleForm.headImg" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </div>
                </div>
                <div class="text item">
                    <span><i class="el-icon-s-custom"></i>用户名称</span>
                    <span>{{userinfo.name}}</span>
                </div>
                <div class="text item">
                    <span><i class="el-icon-phone"></i>电话号码</span>
                    <span>{{userinfo.phone}}</span>
                </div>
                <div class="text item">
                    <span><i class="el-icon-date"></i>创建日期</span>
                    <span>{{userinfo.createTime}}</span>
                </div>
            </el-card>
        </el-col>
        <el-col :span="12" style="margin-left: 20px">
            <el-card class="box-card user-info-card">
                <div slot="header" class="clearfix">
                    <el-tabs v-model="activeName">
                        <el-tab-pane label="基本资料" name="基本资料">
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="width: 100%;">
                                <el-form-item label="昵称：" prop="name">
                                    <el-input v-model="ruleForm.nickName"></el-input>
                                </el-form-item>
                                <el-form-item label="姓名：" prop="name">
                                    <el-input v-model="ruleForm.name"></el-input>
                                </el-form-item>
                                <el-form-item label="性别：" prop="sex">
                                    <el-select v-model="ruleForm.sex" placeholder="请选择性别">
                                        <el-option label="男" value="0"></el-option>
                                        <el-option label="女" value="1"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="年龄：" prop="age" >
                                    <el-input type="age" v-model.number="ruleForm.age" autocomplete="off"></el-input>
                                </el-form-item>
                                <el-form-item label="身份证号：" prop="idCard">
                                    <el-input v-model="ruleForm.idCard"></el-input>
                                </el-form-item>
                                <el-form-item label="电话号码：" prop="phone" >
                                    <el-input v-model="ruleForm.phone"></el-input>
                                </el-form-item>
                                <el-form-item label="所在城市：" prop="cityCode">
                                    <el-select v-model="ruleForm.cityCode" filterable placeholder="请选择">
                                        <el-option
                                            v-for="item in city"
                                            :key="item.treeCode"
                                            :label="item.name"
                                            :value="item.treeCode">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" plain @click="submitForm('ruleForm')" >保存</el-button>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                        <el-tab-pane label="修改密码" name="修改密码">
                            <el-form :model="updatePasswordFrom" :rules="updatePasswordRules" ref="updatePassword" label-width="120px" class="demo-ruleForm" style="width: 100%;">
                                <el-form-item label="旧密码：">
                                    <el-input v-model="updatePasswordFrom.oldPassword" show-password></el-input>
                                </el-form-item>
                                <el-form-item label="新密码：" prop="newPassword" >
                                    <el-input v-model="updatePasswordFrom.newPassword" show-password></el-input>
                                </el-form-item>
                                <el-form-item label="确认新密码：" prop="confirmPassword" >
                                    <el-input v-model="updatePasswordFrom.confirmPassword" show-password></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" plain @click="updatePassword('updatePassword')" >保存</el-button>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
let Base64 = require('js-base64').Base64;
import {client} from "../../utils/alioss";
import user from "@/api/user";
import validate from "@/utils/validate";
export default {
    data() {
        return {
            /*加载中*/
            loading:false,
            /*用户信息*/
            userinfo:'',
            activeName:'基本资料',
            /*基本信息表单*/
            ruleForm: {
                headImg: '',
                nickName:'',
                name: '',
                sex: '',
                age: '',
                idCard:'',
                phone: '',
                cityCode: '',
            },
            rules: {
                name: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur'}
                ],
                age: [
                    {required: true, message: '年龄不能为空'},
                    {type: 'number', message: '年龄必须为数字'}
                ],
                sex: [
                    {required: true, message: '请选择性别', trigger: 'change'}
                ],
                idCard: [
                    {
                        validator:(rule, value, callback) => {
                            validate.validIdCard(value)?callback(): callback(new Error("身份证格式不正确"));
                        },
                        trigger: 'blur'
                    }
                ],
                phone: [
                    {
                        validator:(rule, value, callback) => {
                            validate.validPhone(value)?callback(): callback(new Error("手机号格式不正确"));
                        },
                        trigger: 'blur'
                    }
                ],
                cityCode: [
                    {required: true, message: '请选择所在城市', trigger: 'blur'}
                ]
            },
            /*修改密码表单*/
            updatePasswordFrom:{
                oldPassword:'',
                newPassword:'',
                confirmPassword:'',
            },
            updatePasswordRules:{
                newPassword: [
                    {
                        validator:(rule, value, callback) => {
                            validate.validPassword(value)?callback(): callback(new Error("以数字开头，长度在6~18之间，只能包含字母、数字和下划线"));
                        },
                        trigger: 'blur'
                    }
                ],
                confirmPassword: [
                    {
                        validator:(rule, value, callback) => {
                            validate.validPassword(value)?callback(): callback(new Error("以数字开头，长度在6~18之间，只能包含字母、数字和下划线"));
                        },
                        trigger: 'blur'
                    }
                ],
            },
            /*城市列表*/
            city:[],
        };
    },
    created() {
        this.getUserInfo();
        this.getCity();
    },
    methods: {
        /*查询用户信息*/
        getUserInfo(){
            this.loading=true;
            user.getUserInfo().then(res=>{
                this.userinfo=res.data;
                this.ruleForm.headImg = res.data.headImg;
                this.ruleForm.nickName = res.data.nickName;
                this.ruleForm.name = res.data.name;
                this.ruleForm.sex = res.data.sex
                this.ruleForm.age = res.data.age
                this.ruleForm.idCard = res.data.idCard
                this.ruleForm.phone =  res.data.phone;
                this.ruleForm.cityCode = res.data.cityCode
                this.loading=false;
            }).catch(err=>{
                this.loading=false;
            })
        },
        /*查询城市*/
        async getCity() {
            //如果已经加载过不加载
            if(this.$store.getters.allCity.length==0){
                await this.$store.dispatch('getAllCity')
            }
            this.city=this.$store.getters.allCity[0].list;
        },
        /*上传头像成功后*/
        handleAvatarSuccess(res, file) {
            this.ruleForm.headImg = res;
            this.msgSuccess("上传成功!")
        },
       /*上传头像失败后*/
        handleError() {
            this.msgError("上传失败,请重新上传图片!");
        },
        /*上传头像*/
        async Upload(file) {
            let fileName = 'user' + `${Date.parse(new Date())}` + '.png';  //定义唯一的文件名
            let url;
            await client().multipartUpload(fileName, file.file, {
                headers: {
                    'Content-Disposition': 'inline',//设置头部，否则阿里oss的地址是下载而不是浏览图片
                    'Content-Type': 'png'//注意：根据图片或者文件的后缀来设置
                }
            }).then(result => {
                //得到上传后的文件地址
                let index = result.res.requestUrls[0].indexOf('?')
                if (index == -1) {
                    url = result.res.requestUrls[0]
                } else {
                    url = result.res.requestUrls[0].substring(0, index)
                }
            })
            return url
        },
        /*上传头像之前*/
        beforeAvatarUpload(file) {
            const isPNG = file.type === 'image/png';
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isPNG) {
                this.$message.error('上传头像图片只能是 PNG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isPNG && isLt2M;
        },
        /*修改基本信息*/
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    user.updateUserInfo(this.ruleForm).then(res=>{
                        this.msgSuccess('信息修改成功');
                        this.getUserInfo();
                    })
                }
            })
        },
        /*修改密码*/
        updatePassword(formName){
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    let oldPassword=Base64.encode(this.updatePasswordFrom.oldPassword);
                    let newPassword=Base64.encode(this.updatePasswordFrom.newPassword);
                    let confirmPassword=Base64.encode(this.updatePasswordFrom.confirmPassword);
                    user.updateUserPassword({oldPassword,newPassword,confirmPassword}).then(res=>{
                        this.msgSuccess('修改成功');
                        this.updatePasswordFrom.oldPassword='';
                        this.updatePasswordFrom.newPassword='';
                        this.updatePasswordFrom.confirmPassword='';
                    })
                }
            });
        }
    }
}
</script>

<style scoped>
/*头像*/
.head-img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    overflow: hidden;
    margin:0 auto;
}

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
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
}

.avatar {
    width: 100px;
    height: 100px;
    display: block;
}

/*el卡片*/
.user-info-card {
    font-size: 20px;
    text-align: left;
}
.text {
    font-size: 14px;
    border-bottom: 1px solid #ccc;
    padding: 10px;
    display: flex;
    justify-content: space-between;
}
</style>
