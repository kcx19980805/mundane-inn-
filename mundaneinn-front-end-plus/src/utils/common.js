export default {
    /*-----------------------------------------------日期类-------------------------------------------------------------*/
    /**
     * 将Date类型日期格式转换为字符串
     * @param date
     * @param format 默认 yyyy-mm-dd | yyyy-mm-dd hh:mm:ss
     * @returns {string}
     */
    dateFormat(_date,_format) {
        let date = new Date(_date);
        let year = date.getFullYear();
        let month = date.getMonth() < 9 ? "0" + (date.getMonth() + 1) : "" + (date.getMonth() + 1);
        let day = date.getDate() < 10 ? "0" + date.getDate() : "" + date.getDate();
        let hour = date.getHours() < 10 ? "0" + date.getHours() : "" + date.getHours();
        let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : "" + date.getMinutes();
        let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : "" + date.getSeconds();
        if(_format == "yyyy-mm-dd hh:mm:ss"){
            return (year + "-" + month + "-" + day + " "+ hour + ":"+minutes + ":"+seconds);
        }
        return (year + "-" + month + "-" + day);
    },
    /**
     * 计算两个日期相隔的天数
     * @param sDate1
     * @param sDate2
     * @returns {number}
     */
    dateDiff(sDate1, sDate2) {    //sDate1和sDate2是2006-12-18格式
        let aDate, oDate1, oDate2, iDays
        aDate = sDate1.split("-")
        oDate1 = new Date(aDate[0], aDate[1], aDate[2])
        aDate = sDate2.split("-")
        oDate2 = new Date(aDate[0], aDate[1], aDate[2])
        //把相差的毫秒数转换为天数
        iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24)
        return iDays
    },
    /**
     * 订单倒计时
     * @param createDate 创建时间（'0000-00-04 00:00:00'）
     * @param limitTimes limitTimes 时间，单位分钟
     * @returns {{min: number, second: number}}
     */
    countDown(createDate,limitTimes){
        limitTimes=limitTimes*60
        let now=new Date()
        let create=new Date(createDate)
        let nowSecond=now.getTime()
        let createSecond=create.getTime()
        let countSecond=parseInt((nowSecond-createSecond)/1000)
        let times= limitTimes-countSecond
        if(times<=0){
            times=0;
        }
        let minutes = parseInt(times/60) < 10 ? "0" + parseInt(times/60): parseInt(times/60) ;
        let seconds = parseInt(times%60) < 10 ? "0" + parseInt(times%60): parseInt(times%60);
        return {
            min:minutes,
            second:seconds
        }
    },
    /*-----------------------------------------------数组类-------------------------------------------------------------*/
    /**
     * 小数组是否被大数组包含,是返回true,不是返回false
     * @param max 大数组
     * @param min 小数组
     * @returns {boolean}
     */
    arrayIsContained:(max, min)=>{
        if(!(max instanceof Array) || !(min instanceof Array)) return false;
        if(max.length < min.length) return false;
        let aStr = max.toString();
        for(let i = 0, len = min.length; i < len; i++){
            if(aStr.indexOf(min[i]) == -1) return false;
        }
        return true;
    },
    /**
     * 根据key对json进行排序
     * @param jsonArray
     * @param key
     * @returns {*}
     */
    jsonSortByKey(jsonArray,key) {
        return jsonArray.sort(function (a, b) {
            let x = a[key];
            let y = b[key];
            return ((x < y) ? -1 : ((x > y) ? 1 : 0));
        });
    },

    /**
     * 解析百度地图的地址为字符串
     * @param geo
     */
    parseBaiDuMap(geo){
        let address="";

        if(geo.province != ""){
            address+=geo.province+"-"
        }

        if(geo.city != ""){
            address+=geo.city+"-"
        }

        if(geo.district != ""){
            address+=geo.district+"-"
        }else{
            address=address.substring(0,address.length-1)
        }

        if(geo.street != ""){
            address+=geo.street+"-"
        }else{
            address=address.substring(0,address.length-1)
        }

        if(geo.streetNumber != ""){
            address+=geo.streetNumber
        }else{
            address=address.substring(0,address.length-1)
        }
        return address;
    },
    /**
     * 移除数组中的指定元素
     * @param arr 数组
     * @param val 元素
     */
    removeByValue(arr, val) {
        for (let i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                arr.splice(i, 1);
                break;
            }
        }
    },

    /*-----------------------------------------------div类-------------------------------------------------------------*/
    /**
     * 使div可拖拽
     * @param id 元素的id
     */
    dragFunc(id) {
        let Drag = document.getElementById(id);
        Drag.onmousedown = function(event) {
            let ev = event || window.event;
            event.stopPropagation();
            let disX = ev.clientX - Drag.offsetLeft;
            let disY = ev.clientY - Drag.offsetTop;
            document.onmousemove = function(event) {
                let ev = event || window.event;
                Drag.style.left = ev.clientX - disX + "px";
                Drag.style.top = ev.clientY - disY + "px";
                Drag.style.cursor = "move";
            };
        };
        Drag.onmouseup = function() {
            document.onmousemove = null;
            this.style.cursor = "default";
        };
    }
}

