<!--聊天框-->
<template>
    <!--弹出聊天框-->
    <div class="chat" id="chatDiv" v-if="showChat" :style="chatStyle">
        <div class="chat-head">
            <span class="online-state">{{userInfo.name}}{{userInfo.isOnline=='1'?'(在线)':' (该用户不在线)'}}</span>
            <span class="el-icon-close icon-close" @click="close()"></span>
        </div>
        <div class="chat-contain" ref="chatBox">
            <div class="chatItem" v-for="(item,index) in chatList" :key="index" :class="{'self':item.sendUserId==$store.getters.userId}">
                <div class="header">
                    <img :src="item.sendUserId==$store.getters.userId?$store.getters.avatar:userInfo.headImg" alt="">
                </div>
                <div class="chatContent">{{item.content}}</div>
            </div>
        </div>
        <div class="chat-footer">
            <el-row>
                <VueEmoji ref="emoji" :value="chatContent" @input="onInput" width="300" height="80" />
                <el-button type="primary" size="mini"  style="right: 10px;top: 50px;position: absolute;" @click="sendMsg()">发送消息</el-button>
            </el-row>
        </div>
    </div>
</template>

<script>
    import VueEmoji from 'emoji-vue'
    import user from "@/api/user";
    import chat from "@/api/chat";
    export default {
        components:{ VueEmoji},
        //是否显示、对方的id、样式
        props:["isShow","userId","chatStyle"],
        data(){
            return{
                /*输入的框内容*/
                chatContent:"",
                /*聊天内容*/
                chatList:[],
                /*对方的用户信息*/
                userInfo:[],
                /*websocket*/
                websocket:"",
                /*加载中*/
                loading:false,
            }
        },
        created() {
        },
        mounted() {
            this.loading=true;
            Promise.all([
                this.connectWebSocket(),
                this.getUserInfo(),
                this.getChat()
            ]).then(res=>{
                //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
                window.addEventListener('onbeforeunload', ()=>{this.websocket.close();}, true)
                this.loading=false;
            })
        },
        updated() {
            //在页面更新时下拉滚动条
            this.toBottom();
            //页面更新且打开时
            if(this.showChat){
              //窗口可拖拽
              this.common.dragFunc("chatDiv");
              //所有消息设为已读
              chat.isReadChat(this.$store.getters.userId,this.userId)
            }
        },
        destroyed () {
            //关闭WebSocket
            if(this.websocket){
                this.websocket.close();
            }
        },
        methods:{
            /*查询对方信息*/
            getUserInfo(){
                return new Promise((resolve, reject) => {
                    user.getOtherUserInfo(this.userId).then(res=>{
                        this.userInfo=res.data
                        resolve();
                    }).catch(()=>{
                        reject();
                    })
                });
            },
            /*查询之前的聊天记录*/
            getChat(){
                return new Promise((resolve, reject) => {
                    chat.getChatList(this.$store.getters.userId,this.userId).then(res=>{
                        this.chatList=res.data;
                        resolve();
                    }).catch(()=>{
                        reject();
                    })
                });
            },
            /*表情输入*/
            onInput (event) {
                this.chatContent = event.data
            },
            /*连接websocket*/
            connectWebSocket () {
                return new Promise((resolve, reject) => {
                    let isHttps = 'https:' == document.location.protocol ? true : false;
                    let serviceIp = '127.0.0.1:8888';
                    let path="/socket/client?userId="+this.$store.getters.userId;
                    if('WebSocket' in window) {
                        this.websocket = isHttps?new WebSocket("wss://"+serviceIp+path):new WebSocket("ws://"+serviceIp+path);
                    } else if('MozWebSocket' in window) {
                        this.websocket = isHttps?new MozWebSocket("wss://"+serviceIp+path):new MozWebSocket("ws://"+serviceIp+path);
                    } else {
                        this.$message.error('您的浏览器不支持WebSocket!');
                        return false;
                    }
                    // 监听socket连接
                    this.websocket.onopen = ()=>{
                        console.log("WebSocket连接成功")
                    }
                    // 监听socket错误信息
                    this.websocket.onerror = ()=>{
                        this.$message.error('WebSocket连接错误!');
                        reject();
                    }
                    //监听socket服务器消息
                    this.websocket.onmessage = (msg)=>{
                        console.log("收到消息")
                        console.log(msg)
                        this.chatList.push(JSON.parse(msg.data))
                    }
                    //监听socket关闭
                    this.websocket.onclose = ()=> {
                        console.log("WebSocket连接关闭");
                    }
                    resolve();
                });
            },
            /*发送消息*/
            sendMsg: function () {//发送消息给指定的人
                let chat ={
                    "sendUserId": this.$store.getters.userId,
                    "acceptUserId": this.userId,
                    "content": this.chatContent,
                    "isRead": "0",
                    "createTime": new Date(),
                    "isDelSend": "0",
                    "isDelAccept": "0"
                };
                this.chatList.push(chat)
                this.websocket.send(JSON.stringify(chat))
                this.chatContent=""
                this.$refs.emoji.clear()
            },
            /*关闭聊天框*/
            close () {
                this.showChat=false
            },
            /*让滚动条自动滚到最下面*/
            toBottom(){
                let chatBox = this.$refs.chatBox;
                if(typeof(chatBox) !== "undefined"){
                  chatBox.scrollTop = chatBox.scrollHeight - chatBox.clientHeight;
                }
            },
        },
        computed:{
            /*由于父组件传值到子组件是单向传值，用计算属性更改到父组件实现双向传值*/
            showChat:{
                get(){
                    return this.isShow
                },
                set(val){
                  this.$emit("update:isShow",val);
                  //刷新用户在线状态
                  this.getUserInfo();
                }
            }
        },
    }
</script>

<style scoped>
    /*聊天框*/
    .chat{
        z-index: 999;
        position: fixed;
        width: 400px;
        height: 300px;
        background-color: #e8e8e8;
        overflow: hidden;
    }
    .chat-head{
        width: 100%;
        height: 40px;
        background-color: white;
        display: flex;
        justify-content: flex-start;
        background-color: #e8e8e8;
    }
    .online-state{
        display: block;
        width: 90%;
        height: 37px;
        line-height: 42px;
        font-weight: bold;
        font-size: 15px;
    }
    .icon-close{
        line-height: 35px;
        cursor: pointer;
        font-size: 20px;
        height: 30px;
        margin-top: 5px;
        width: 30px;
    }
    .chat-contain{
        width: 100%;
        margin:5px 0;
        /*适应父盒子的高度*/
        height: calc(100% - 140px);
        background-color: white;
        overflow: auto;
    }
    .chat-footer{
        width: 100%;
        height:100px;
        padding-left: 5px;
        text-align: left;
    }
    .chat-left{
        width: 60%;
        height: 100%;
    }
    .chat-right{
        width: 40%;
        height: 100%;
    }
    /*滚动条样式*/
    *::-webkit-scrollbar {
        /*滚动条整体样式*/
        width : 5px;  /*高宽分别对应横竖滚动条的尺寸*/
        height: 0px;
    }
    *::-webkit-scrollbar-thumb {
        /*滚动条里面小方块*/
        border-radius   : 10px;
        background-color: #a2a3a5;
    }
    *::-webkit-scrollbar-track {
        /*滚动条里面轨道*/
        box-shadow   : inset 0 0 5px rgba(0, 0, 0, 0.2);
        background   : #ededed;
        border-radius: 10px;
    }
    /*消息样式*/
    .chatItem{
        display: flex;
        margin: 5px 10px;
    }
    .self{
        flex-direction: row-reverse;
        justify-content: flex-start;

    }
    .chatItem .header img{
        width: 50px;
        height: 50px;
        border-radius: 50%
    }
    .chatItem .chatContent{
        background: #bbb;
        border-radius: 5px;
        padding: 0px 5px;
        color: #fff;
        margin: 8px 0px 0px 20px;
        line-height: 34px;
        position: relative;
        height: 34px;
    }
    .self .chatContent{
        margin: 8px 20px 0px 0px;
    }
    .chatItem .chatContent::before{/*伪类制造消息框的小箭头 */
        display: block;
        content: "";
        position: absolute;
        width: 0;
        height: 0;
        border-right: 10px solid #bbb;
        border-top: 10px solid transparent;
        border-bottom: 5px solid transparent;
        top: 10px;
        left: -10px;
    }
    .self .chatContent::before{
        display: block;
        content: "";
        position: absolute;
        width: 0;
        height: 0;
        border-left: 10px solid #bbb;
        border-top: 10px solid transparent;
        border-bottom: 5px solid transparent;
        top: 10px;
        right: -10px;
        left: initial;
        border-right:initial;
    }

    .header{
        position: relative;
        font-size: 18px;
        font-weight: 900;
        height: 50px;
        text-align: center;
        line-height: 40px;
    }
</style>
