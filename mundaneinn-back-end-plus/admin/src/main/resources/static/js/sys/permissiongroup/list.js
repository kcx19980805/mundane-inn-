
//表格参数及列
var params = {
    id: "#permissionGroupTable",     //初始化表格id
    url: "sys/permissionGroup/list",    //请求接口路径
    toolbarId: "#toolbar",  //按钮组id
    sortOrder: "asc",       //列表序号iid 默认asc升序 desc降序
    uniqueId: "permissionGroupId",         //表格每行数据的唯一标识符 默认为id
    singleSelect: false,    //表格复选框是否单选  true单选  默认false多选
    rememberSelected: true,      //翻页是否保存已选数据 true保存 默认false不保存
    oneCheckedClassName: {edit: "a#edit"},
    moreCheckedClassName: {del: "a#del"},
    columns: [
        {
            field: 'iid', title: '序号', align: 'center'
        },
        {
            field: 'name', title: '角色名称', align: 'center'
        },
        {
            field: 'permissionGroupId',title: '操作',align: 'center',
            formatter: function (value, row, index) {
                return '<a class="label label-info" onclick="permissionGroupEdit(\'' + value + '\')">编辑</a>&nbsp;&nbsp;<a class="label label-danger" onclick="permissionGroupDel(\'' + value + '\')">删除</a>';
            }
        }
    ],
    defaultKeyIds: {
        keyword: "#keyword"           //组名
    }
};
$(document).ready(function () {
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

// 新增
$(".permissionGroupAdd").on("click", function () {
    $.modal.open("新增管理组", "add.html", "确定", "关闭", false, 800);
});

// 修改
function permissionGroupEdit(permissionGroupId) {
    $.modal.open("编辑管理组", "edit.html?permissionGroupId=" + permissionGroupId, "确定", "关闭", false, 800);
}

// 删除
function permissionGroupDel(permissionGroupId) {
    $.modal.confirm("是否确定删除?", function () {
        $.operate.postEntity("sys/permissionGroup/delete", {permissionGroupId: permissionGroupId}, function (res) {
            $.modal.msgSuccessDelay("操作成功", function () {
                tableRefresh("#permissionGroupTable");
                $.modal.lock = false;
            });
        })
    })
}

