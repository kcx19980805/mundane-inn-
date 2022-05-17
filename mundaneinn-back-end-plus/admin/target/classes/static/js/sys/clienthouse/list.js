//表格参数及列
var params = {
    id: "#clientHouseTable",     //初始化表格id
    url: "sys/clientHouse/list",    //请求接口路径
    toolbarId: "#toolbar",  //按钮组id
    sortOrder: "asc",       //列表序号iid 默认asc升序 desc降序
    uniqueId: "id",         //表格每行数据的唯一标识符 默认为id
    singleSelect: false,    //表格复选框是否单选  true单选  默认false多选
    rememberSelected: true,      //翻页是否保存已选数据 true保存 默认false不保存
    oneCheckedClassName: {edit: "a#edit"},
    moreCheckedClassName: {del: "a#del"},
    columns: [
        {
            field: 'iid', title: '序号', align: 'center'
        },
        {
            field: 'name', title: '房源名称', align: 'center',width:'200px'
        },
        {
            field: 'housePrice', title: '房源价格', align: 'center'
        },
        {
            field: 'discount', title: '折扣', align: 'center'
        },
        {
            field: 'rentalType', title: '出租类型', align: 'center',
            formatter: function (value, row, index) {
                return value == 0 ? "单间" : "整套";
            }
        },
        {
            field: 'peopleNumber', title: '人数', align: 'center'
        },
        {
            field: 'bedNumber', title: '床数', align: 'center'
        },
        {
            field: 'houseType', title: '户型', align: 'center'
        },
        {
            field: 'status', title: '房源状态', align: 'center',
            formatter: function (value, row, index) {
                if(value == 0){
                    return "待审核";
                }else if(value == 1){
                    return "已上架";
                }else if(value == 2){
                    return "审核失败";
                }
                return "未知";
            }
        },
        {
            field: 'id', title: '操作', align: 'center',
            formatter: function (value, row, index) {
                var html = '<a class="label label-info" onclick="detail(\'' + row.images + '\')">查看照片</a>';
                if (row.status == 0) {
                    html += '&nbsp;&nbsp;<a class="label label-primary" onclick="pass(\'' + value + '\')">通过</a>';
                    html += '&nbsp;&nbsp;<a class="label label-danger" onclick="refuse(\'' + value + '\')">拒绝</a>';
                }
                return html
            }
        }
    ],
    //单选框 多选框 不用传
    defaultKeyIds: {
        houseName: "#houseName",
        state: "#state"
    }
};
$(document).ready(function () {
    //初始化表格
    tableInit(params);
});

//表格选中 取消选中 全选 取消全选 的回调函数 不可删除
function tableCallBack(datas, rows) {
}

// 重置
$(".tableRest").on("click", function () {
    $.form.reset('#' + $(this).attr("data-form"));
    tableRefresh('#' + $(this).attr("data-table"));
});

// 搜索/刷新
$(".tableSearch").on("click", function () {
    tableRefreshParams('#' + $(".tableSearch").attr("data-table"));
});

//查看照片
function detail(images){
    console.log(images)
    console.log(JSON.stringify(images))
    $.modal.open("查看房源照片", "detail.html?images=" + images, "", "关闭", false, 800,600);
}

//通过
function pass(userId) {
    $.operate.postEntity("sys/clientHouse/update", {id:userId,state:'1',reason:''}, function (res) {
        $.modal.msgSuccessDelay("操作成功", function () {
            tableRefresh("#clientHouseTable");
            $.modal.lock = false;
        });
    })
}
// 拒绝
function refuse(userId) {
    $.modal.prompt("拒绝房源", 0,"","请输入原因","",function (index, value){
        $.operate.postEntity("sys/clientHouse/update", {id:userId,state:'2',reason:value}, function (res) {
            $.modal.msgSuccessDelay("操作成功", function () {
                tableRefresh("#clientHouseTable");
                layer.close(index);
                $.modal.lock = false;
            });
        })
    });
}

