//树形图 默认设置
var settingss = {
    data: {
        simpleData: {
            enable: true,  //true 、 false 分别表示 使用 、 不使用 简单数据模式
            idKey: "id",  //节点数据中保存唯一标识的属性名称
            pIdKey: "parentId",    //节点数据中保存其父节点唯一标识的属性名称
            rootPId: -1  //用于修正根节点父节点数据，即 pIdKey 指定的属性值
        },
        key: {
            name: "name"  //zTree 节点数据保存节点名称的属性名称  默认值："name"
        }
    },
    check: {
        enable: true,  //true 、 false 分别表示 显示 、不显示 复选框或单选框
        nocheckInherit: true  //当父节点设置 nocheck = true 时，设置子节点是否自动继承 nocheck = true
    },
    callback: {}
};
$(document).ready(function () {
    $("#menuId").empty();
    //获取所有菜单树列表
    //加载权限树形图
    $.operate.get("sys/menu/treeMenuList", function (res) {
        $.fn.zTree.init($("#menuId"), settingss, res.data);

        // 获取管理组的权限 并 选中对应树形图
        $.operate.get("sys/permissionGroup/info/" + getValueByUrl('permissionGroupId'), function (res) {
            var data = res.data;
            if (data) {
                $('#name').val(data.name);
                var menuIds = data.menuIds;
                if (menuIds != null && menuIds !== "" && menuIds.length > 0) {
                    var treeObj = $.fn.zTree.getZTreeObj("menuId");
                    var nodes = treeObj.getNodes();
                    if (nodes.length > 0) {
                        for (var i = 0; i < menuIds.length; i++) {
                            var node = treeObj.getNodeByParam("id", menuIds[i]);
                            if (node != null) {
                                treeObj.checkNode(node, true)
                            }
                        }
                    }
                }
            }
        });
    });
});

// 修改 确定
function submitHandler(index, layero) {
    //获取菜单及操作权限 menuId
    var treeObj = $.fn.zTree.getZTreeObj("menuId");
    var nodes = treeObj.getCheckedNodes(true);
    var menuId = "";
    if (nodes.length == 0) {
        $.modal.msgWarning("请选择管理组操作权限");
        return false;
    }
    //菜单id 多个逗号隔开
    for (var i = 0; i < nodes.length; i++) {
        menuId = menuId + "," + nodes[i].id;
    }
    var data = {
        permissionGroupId: getValueByUrl('permissionGroupId'),
        name: $('#name').val(),
        menuId: menuId.substring(1)
    };
    $.operate.postEntity('sys/permissionGroup/update', data, function () {
        $.modal.msgSuccessDelay("操作成功", function () {
            $.modal.lock = false;
            //关闭当前弹窗
            parent.layer.close(index);
            //刷新父级列表
            parent.tableRefresh("#permissionGroupIdTable");
        });
    });
}