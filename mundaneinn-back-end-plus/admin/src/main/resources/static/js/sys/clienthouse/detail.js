$(document).ready(function () {
    let html=''
    let images = getValueByUrl("images").split(",");
    for (let i = 0; i < images.length; i++) {
        html +="<img id=\"imgUrl\" src=\""+images[i]+"\" onclick=\"showBigImage(this, 2)\" style=\"cursor: pointer;width: 45%;margin:0 5px 5px 0;height:200px;object-fit:cover;border-color: #bdbdbd;\"/>";
    }
    $(".img").append(html)
});

//确定
function submitHandler(index, layero) {
/*    var data = {
        bannerId: getValueByUrl("bannerId"),
        name: $("#name").val(),
        link: $("#link").val(),
        img: $("#img").attr("url"),
        order:$("#order").val()
    };
    $.operate.postEntity('sys/clientBanner/update', data, function (result) {
        $.modal.msgSuccessDelay("操作成功", function () {
            parent.tableRefresh("#clientBannerTable");
            parent.layer.close(index);
            $.modal.lock = false;
        });
    });*/
}
