$(function () {
    // 获取详情
    $.operate.get("sys/user/info/" + getValueByUrl('userId'), function (r) {
        var data = r.data;
        if (data) {
            $.operate.get("sys/permissionGroup/list?limit=-1&keyword=", function (r) {
                var list = r.data.list;
                if (list) {
                    var options = "";
                    for (var i = 0; i < list.length; i++) {
                        options += ' <option value=\'' + list[i].permissionGroupId + '\'>' + list[i].name + '</option>';
                    }
                    $("#permissionGroupId").append(options).val(data.permissionGroupId);
                    $('#account').val(data.account);
                }
            });
        }
    });
});

//编辑 系统管理员 确定
function submitHandler(index, layero) {
    var data = {
        userId: getValueByUrl("userId"),
        permissionGroupId: $('#permissionGroupId').val(),
    };
    $.operate.postEntity('sys/user/update', data, function () {
        $.modal.msgSuccessDelay("操作成功", function () {
            parent.tableRefresh("#userTable");
            $.modal.lock = false;
            parent.layer.close(index);
        });
    });
}