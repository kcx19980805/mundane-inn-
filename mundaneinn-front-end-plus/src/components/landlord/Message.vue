<!--消息-->
<template>
    <el-row style="height: 100%" v-loading="loading">
        <el-col :span="12" style="height: 100%;">
            <el-card class="box-card user-info-card">
                <div slot="header" class="clearfix">
                    <span>消息列表</span>
                </div>
                <div style="width:100%;height: 600px;overflow: auto;">
                    <div class="text item" style="display: flex;justify-content: space-between" v-for="(item,index) in messageList" :key="index">
                        <div @click="chooseUser(item)"  class="userItem">
                            <div class="not-online unread"   :class="{'online':item.isOnline==1}">
                                <img :src="item.headImg">
                            </div>
                            <div class="right">
                                <div class="username">{{item.userName}} </div>
                                <div class="username">电话:{{item.phone}}</div>
                                <div class="msg">{{item.content}}</div>
                            </div>
                        </div>
                        <div style="min-width: 200px">
                            <el-popconfirm title="确定删除聊天记录吗？" @confirm="deleteChat(item)">
                                <el-button slot="reference" type="text" style="margin-left: 50px"><i class="el-icon-delete"></i>删除</el-button>
                            </el-popconfirm>
                            <p>{{item.createTime}}</p>
                        </div>
                    </div>
                </div>
            </el-card>
        </el-col>
        <el-col :span="6" style="margin-left: 20px;">
            <Chat :is-show.sync="showChat" v-if="currentUsername!=''" :key="currentUsername" :user-id="currentUsername" :chat-style="'right: 150px; bottom: 250px;'"></Chat>
        </el-col>
    </el-row>
</template>

<script>
import Chat from "@/components/Chat";
import chat from "@/api/chat";
export default {
    components: {Chat},
    data(){
        return {
            /*消息列表*/
            messageList: [],
            /*打开聊天框*/
            showChat:false,
            /*当前聊天对象*/
            currentUsername:"",
            /*定时器*/
            timer:null,
            /*加载中*/
            loading:false,
        }
    },
    created() {
        window.parentMounted = this._isMounted;
    },
    async mounted(){
        /*        this.timer = setInterval(() => {
                    this.getChatUserList()
                }, 3000);*/
        await this.getChatUserList();
    },
    destroyed() {
        clearInterval(this.timer);
    },
    methods:{
        //查询与当前用户存在聊天记录的所有用户和最后一条消息
        getChatUserList(){
            chat.getChatUserList(this.$store.getters.userId).then(res=>{
                this.messageList=res.data;
                this.loading=false;
            })
        },
        /*删除聊天*/
        deleteChat(item){
            this.loading=true;
            let otherUserId;
            if(item.sendUserId==this.$store.getters.userId){
                otherUserId=item.acceptUserId;
            }
            else{
                otherUserId=item.sendUserId;
            }
            chat.deleteChat(this.$store.getters.userId,otherUserId).then(res=>{
                this.getChatUserList();
            })
        },
        /*选择用户开始聊天*/
        chooseUser(item){
            if(item.sendUserId==this.$store.getters.userId){
                this.currentUsername=item.acceptUserId;
            }
            else{
                this.currentUsername=item.sendUserId;
            }
            this.showChat=true;
        }
    },
    watch:{

    }
}
</script>

<style scoped>
.unread{
    position: relative;
}
.unread::before{/*未读消息的小红点伪类*/
    position: absolute;
    content: "";
    display: block;
    width: 10px;
    height: 10px;
    border-radius: 5px;
    background: red;
    bottom:5px ;
    right: 5px;
}
.userItem{
    display:flex;
    height: 80px;
    width: 100%;
    background: white;
    align-items: center;
    padding: 0 10px;
    cursor: pointer;
}
.username{
    padding-bottom: 2px;
    text-align: left;
}
.userItem .right{
    padding: 0 10px;
}
/*未在线显示灰色*/
.userItem .not-online{
    filter: grayscale(1)
}
.userItem .not-online img{
    width: 60px;
    height: 60px;
    border-radius: 50%;
}
/*用户在线*/
.userItem .online{
    filter: none
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
}
</style>
