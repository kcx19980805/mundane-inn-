$(document).ready(function () {
    $.operate.get("sys/clientUser/info/" + getValueByUrl("userId"), function (r) {
        var data = r.data;
        if (data) {
             $("#userId").val(getValueByUrl("userId"));
             $('#account').val(data.account);
             $('#nickName').val(data.nickName);
             $('#phone').val(data.phone);
             $('#name').val(data.name);
             $('#sex').val(data.sex);
             $('#email').val(data.email)
        }
    });
});

//添加 确定
function submitHandler(index, layero) {
    var data = {
        userId:$("#userId").val(),
        account: $.base64.encode($('#account').val()),
        nickName: $('#nickName').val(),
        phone: $('#phone').val(),
        name: $('#name').val(),
        sex: $('#sex').val(),
        email:$('#email').val()
    };
    $.operate.postEntity('sys/clientUser/update', data, function () {
        $.modal.msgSuccessDelay("操作成功", function () {
            $.modal.lock = false;
            //关闭当前弹窗
            parent.layer.close(index);
            //刷新父级列表
            parent.tableRefresh("#clientUserTable");
        });
    });
}