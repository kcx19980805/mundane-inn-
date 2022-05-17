//表格参数及列
var params = {
    id: "#userTable",     //初始化表格id
    url: "sys/user/list",    //请求接口路径
    toolbarId: "#toolbar",  //按钮组id
    sortOrder: "asc",       //列表序号iid 默认asc升序 desc降序
    uniqueId: "userId",         //表格每行数据的唯一标识符 默认为id
    singleSelect: false,    //表格复选框是否单选  true单选  默认false多选
    rememberSelected: true,      //翻页是否保存已选数据 true保存 默认false不保存
    oneCheckedClassName: {edit: "a#edit"},
    moreCheckedClassName: {del: "a#del"},
    columns: [
        {
            field: 'iid', title: '序号', align: 'center'
        },
        {
            field: 'account', title: '账号', align: 'center'
        },
        {
            field: 'permissionGroupName', title: '角色', align: 'center'
        },
        {
            field: 'status', title: '状态', align: 'center',
            formatter: function (value, row, index) {
                return value == 0 ? "启用" : "禁用";
            }
        },
        {
            field: 'userId', title: '操作', align: 'center',
            formatter: function (value, row, index) {
                var html = '<a class="label label-info" onclick="userEdit(\'' + value + '\')">编辑</a>&nbsp;&nbsp;<a class="label label-danger" onclick="userDel(\'' + value + '\')">删除</a>';
                if (row.status == 0) {
                    html += '&nbsp;&nbsp;<a class="label label-primary" onclick="userDisable(\'' + value + '\')">禁用</a>';
                } else {
                    html += '&nbsp;&nbsp;<a class="label label-primary" onclick="userRestore(\'' + value + '\')">启用</a>';
                }
                html +='&nbsp;&nbsp;<a class="label label-primary" onclick="updatePassword(\'' + value + '\')">重置密码</a>';
                return html
            }
        }
    ],
    //单选框 多选框 不用传
    defaultKeyIds: {
        keyword: "#keyword",          //账号
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

// 新增
$(".userAdd").on("click", function () {
    $.modal.open("新增账号", "add.html", "确定", "关闭", false, 800);
});

// 修改
function userEdit(userId) {
    $.modal.open("修改账号", "edit.html?userId=" + userId, "确定", "关闭", false, 800);
}

// 删除
function userDel(userId) {
    $.modal.confirm("是否确定删除?", function () {
        $.operate.postEntity("sys/user/delete", {userId: userId}, function (res) {
            $.modal.msgSuccessDelay("操作成功", function () {
                tableRefresh("#userTable");
                $.modal.lock = false;
            });
        })
    })
}

// 禁用
function userDisable(userId) {
    $.modal.confirm("是否确定禁用?", function () {
        $.operate.postEntity("sys/user/disable", {userId: userId}, function (res) {
            $.modal.msgSuccessDelay("操作成功", function () {
                tableRefresh("#userTable");
                $.modal.lock = false;
            });
        })
    })
}

// 启用
function userRestore(userId) {
    $.modal.confirm("是否确定启用?", function () {
        $.operate.postEntity("sys/user/restore", {userId: userId}, function (res) {
            $.modal.msgSuccessDelay("操作成功", function () {
                tableRefresh("#userTable");
                $.modal.lock = false;
            });
        })
    })
}
//重置密码
function updatePassword(userId){
    $.modal.open("重置密码", "updatepassword.html?userId="+userId, "确定", "关闭", false, 800);
}