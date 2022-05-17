<!--页脚-->
<template>
    <div class="g-footer-layout">
        <div class="link-list">
            <a href="">隐私协议</a><i>| </i>
            <a href="">用户协议</a><i>| </i>
            <a href="">关于我们</a><i>| </i>
            <a href="">我是房客</a><i>| </i>
            <a href="">加入红尘</a><i>| </i>
            <a href="">网站地图</a><i>| </i>
        </div>
        <div class="phone-cont">
            国内客服电话：400-188-1234 国内商服电话：010-81400100 海外及港澳台客服电话：0086-571-28089451 0086-10-80697258
        </div>
        <section class="g-copyright-layout">
                    <span>
                         © 2020 hongchen.com红尘版权所有
                         <a href="">津ICP备17006480号</a>
                         <a href="" class="record-icon"> 京公网安备11010802031847号</a>
                         <a href=""> 营业执照</a>
                         <a href=""> 途家保障计划</a>
                    </span>
            <div class="m-copyright-imgs">
                <a href="" class="u-icon-copyright1"></a>
                <a href="" class="u-icon-copyright2"></a>
                <a href="" class="u-icon-copyright4"></a>
            </div>
        </section>
    </div>
</template>

<script>
    export default {
        name: "Footer"
    }
</script>

<style scoped>
    /*页脚*/
    .g-footer-layout{
        background: #292828;
        color: #dcdcdc;
        text-align: center;
        min-width: 1180px;
    }
    .g-footer-layout .link-list{
        line-height: 1;
        width: 100%;
        padding: 60px 0 16px 0;
    }
    .g-footer-layout .link-list a{
        padding: 0 10px;
        color: #999;
        cursor: pointer;
    }
    .g-footer-layout{
        line-height: 1;
    }
    .g-copyright-layout{
        padding: 13px 0 60px 0;
        margin: 0 auto;
        text-align: center;
        font-size: 12px;
        color: #dcdcdc;
    }
    .g-copyright-layout a{
        color: #dcdcdc;
    }
    .m-copyright-imgs a {
        display: inline-block;
        margin: 16px 5px 0 5px;
    }
    .u-icon-copyright1{
        background-image: url("../assets/images/component-copyright.87720624.png");
        background-position: 0 0;
        width: 140px;
        height: 42px;
    }
    .u-icon-copyright2 {
        background-image: url("../assets/images/component-copyright.87720624.png");
        background-position: 0 -46px;
        width: 140px;
        height: 42px;
    }
    .u-icon-copyright4 {
        background-image: url("../assets/images/component-copyright.87720624.png");
        background-position: 0 -92px;
        width: 108px;
        height: 42px;
    }
</style>
