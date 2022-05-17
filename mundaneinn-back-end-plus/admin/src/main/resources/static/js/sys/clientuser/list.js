//表格参数及列
var params = {
    id: "#clientUserTable",     //初始化表格id
    url: "sys/clientUser/list",    //请求接口路径
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
            field: 'status', title: '状态', align: 'center',
            formatter: function (value, row, index) {
                return value == 0 ? "已启用" : "已禁用";
            }
        },
        {
            field: 'account', title: '账号', align: 'center'
        },
        {
            field: 'nickName', title: '昵称', align: 'center'
        },
        {
            field: 'phone', title: '手机号', align: 'center'
        },
        {
            field: 'name', title: '姓名', align: 'center'
        },
        {
            field: 'sex', title: '性别', align: 'center',
            formatter: function (value, row, index) {
                return value == 0 ? "男" : "女";
            }
        },
        {
            field: 'score', title: '房东评分', align: 'center'
        },
        {
            field: 'balance', title: '余额', align: 'center'
        },
        {
            field: 'email', title: '邮箱', align: 'center'
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
                return html
            }
        }
    ],
    //单选框 多选框 不用传
    defaultKeyIds: {
        account: "#account",          //账号
        phone: "#phone"     //手机
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
// 导入
$(".userImport").on("click", function () {
    $("#userImport").val("");
    $("#userImport").trigger("click");
});

$("#userImport").on("change", function () {
    var ob = $("#userImport");
    var file = ob.val();
    if (!file) {
        $.modal.msgWarning("请重新选择Excel文件");
        return false;
    } else {
        //判断上传文件格式
        if (!file.match(/.xlsx|.xls/i)) {
            $.modal.msgWarning("上传的文件格式不支持，请重新选择xls或xlsx格式文件");
            ob.val("");
            return false;
        } else {
            $.modal.confirm("是否确定导入?", function () {
                var form = new FormData();
                form.append("file", ob[0].files[0]);
                $.ajax({
                    url: baseURL + "sys/clientUser/import",
                    processData: false,  // 不处理数据
                    cache: false,        // 不缓存
                    contentType: false,  // 不设置内容类型
                    type: "post",
                    data: form,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            $.modal.msgSuccess("操作成功");
                            //导入成功 刷新表格
                            tableRefresh('#clientUserTable');
                        } else {
                            $.modal.msgError(result.msg);
                        }
                        $.modal.closeLoading();
                    },
                    error: function (result) {
                        $.modal.closeLoading();
                        $.modal.msgError(result.statusText);
                    }
                });
            })
        }
    }
});

//下载 导入模板
$(".userImportTemplate").on("click", function () {
    window.location.href = baseURL + "common/download/resource?fileName=导入用户模板.xlsx";
});

// 修改
function userEdit(userId) {
    $.modal.open("修改账号", "edit.html?userId=" + userId, "确定", "关闭", false, 800);
}

// 删除
function userDel(userId) {
    $.modal.confirm("是否确定删除?", function () {
        $.operate.postEntity("sys/clientUser/delete", {userId: userId}, function (res) {
            $.modal.msgSuccessDelay("操作成功", function () {
                tableRefresh("#clientUserTable");
                $.modal.lock = false;
            });
        })
    })
}

// 禁用
function userDisable(userId) {
    $.modal.confirm("是否确定禁用?", function () {
        $.operate.postEntity("sys/clientUser/disable/"+userId, null, function (res) {
            $.modal.msgSuccessDelay("操作成功", function () {
                tableRefresh("#clientUserTable");
                $.modal.lock = false;
            });
        })
    })
}

// 启用
function userRestore(userId) {
    $.modal.confirm("是否确定启用?", function () {
        $.operate.postEntity("sys/clientUser/restore/"+userId, null, function (res) {
            $.modal.msgSuccessDelay("操作成功", function () {
                tableRefresh("#clientUserTable");
                $.modal.lock = false;
            });
        })
    })
}



