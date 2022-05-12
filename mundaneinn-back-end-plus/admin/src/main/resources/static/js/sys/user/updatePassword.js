function submitHandler(index, layero) {
    var oldPassword = $('#oldPassword').val();
    var newPassword = $('#newPassword').val();
    var confirmPassword = $('#confirmPassword').val();
    if (newPassword != confirmPassword) {
        $('#confirmPassword').addClass("input-error");
        $('#newPassword').addClass("input-error");
        $.modal.msgWarning("两次输入新密码不匹配");
        return false;
    }else {
        $('#newPassword').removeClass("input-error");
        $('#confirmPassword').removeClass("input-error");
    }
    var data = {
        userId:getValueByUrl("userId"),
        oldPassword: $.base64.encode(oldPassword),
        newPassword: $.base64.encode(newPassword),
        confirmPassword: $.base64.encode(confirmPassword)
    };
    $.operate.postEntity("sys/user/updatePassword", data, function (r) {
        $.modal.msgSuccessDelay("修改成功", function () {
            $.modal.lock = false;
/*            $('#oldPassword').val("");
            $('#newPassword').val("");
            $('#confirmPassword').val("");*/
            //退出重新登录
            //window.location.href = "../../logout";
            //关闭当前弹窗
            parent.layer.close(index);
            //刷新父级列表
            parent.tableRefresh("#userTable");
        });
    })
}
