$(function () {
    // 获取管理组列表
    $.operate.get("sys/permissionGroup/list?limit=-1&keyword=", function (r) {
        var list = r.data.list;
        if (list) {
            var options = "";
            for (var i = 0; i < list.length; i++) {
                options += ' <option value=\'' + list[i].permissionGroupId + '\'>' + list[i].name + '</option>';
            }
            $("#permissionGroupId").append(options);
        }
    });
});


//添加 系统管理员 确定
function submitHandler(index, layero) {
    var data = {
        permissionGroupId: $('#permissionGroupId').val(),
        account: $.base64.encode($('#account').val()),
    };
    $.operate.postEntity('sys/user/save', data, function () {
        $.modal.msgSuccessDelay("操作成功", function () {
            $.modal.lock = false;
            //关闭当前弹窗
            parent.layer.close(index);
            //刷新父级列表
            parent.tableRefresh("#userTable");
        });
    });
}