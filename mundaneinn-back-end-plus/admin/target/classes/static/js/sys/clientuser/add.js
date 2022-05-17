//添加 确定
function submitHandler(index, layero) {
    var data = {
        account: $.base64.encode($('#account').val()),
        nickName: $('#nickName').val(),
        name: $('#name').val(),
        phone: $('#phone').val(),
        sex: $('#sex').val(),
        email:$('#email').val()
    };
    $.operate.postEntity('sys/clientUser/add', data, function () {
        $.modal.msgSuccessDelay("操作成功", function () {
            $.modal.lock = false;
            //关闭当前弹窗
            parent.layer.close(index);
            //刷新父级列表
            parent.tableRefresh("#clientUserTable");
        });
    });
}
