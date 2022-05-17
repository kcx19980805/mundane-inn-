<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style>
*{
  box-sizing: inherit;
}
a {
  text-decoration: none;
  cursor: pointer;
}
ul{
  list-style: none;
  margin: 0;
  padding: 0;
}
nav,section{
  margin: 0;
  display: block;
}
body{
  padding: 0;
  margin: 0;
  font-family: Microsoft Yahei,PingFang SC,Hiragino Sans GB,Helvetica Neue,Helvetica,tahoma,arial,Verdana,sans-serif,WenQuanYi Micro Hei,"\5B8B\4F53";
  font-size: 12px;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
/*滚动条样式*/
*::-webkit-scrollbar {
    /*滚动条整体样式*/
    width: 5px; /*高宽分别对应横竖滚动条的尺寸*/
    height: 0px;
}

*::-webkit-scrollbar-thumb {
    /*滚动条里面小方块*/
    border-radius: 10px;
    background-color: #a2a3a5;
}

*::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #ededed;
    border-radius: 10px;
}
</style>
