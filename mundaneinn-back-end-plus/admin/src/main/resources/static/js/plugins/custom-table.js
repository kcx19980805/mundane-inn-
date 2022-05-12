/**
 * 通用table方法封装处理
 * Copyright (c) 2019 ww
 */
var baseURL = '../../';

var rememberSelectedIds = {};  //全局数组 保存已选择的行数据的唯一标识符
var rememberSelectedRows = {};  //全局数组 保存已选择的行数据的对象
/**
 * 初始化
 * @param id            表格id 带#
 * @param url           访问URL
 * @param toolbarId     工具容器id
 * @param columns       列表定义 eg: columns:[{checkbox: true},{field: 'userId', align: 'center', title: '编号'}]
 * @param uniqueId       表格每行数据的唯一标识符 默认为 id
 * @param sortOrder       表格针对序号的排序方式 默认asc升序 desc降序
 * @param singleSelect    表格复选框是否单选  true单选  默认false多选
 * @param rememberSelected    翻页是否保存已选数据 true保存 默认false不保存  且 若保存时需设置复选框：field: 'state', checkbox: true,
 * @param defaultKeyIds 默认关键字的ids
 */
function tableInit(params) {
    bootstrapTableExtend(params);
}

/// bootstrapTable初始化
function bootstrapTableExtend(params) {
    var oTable = new bootstrapTableInit(params);
    oTable.Init();
}

/// bootstrapTable初始化
var bootstrapTableInit = function (params) {
    var id = params["id"];
    var url = params["url"];
    var toolbarId = params["toolbarId"];
    var columns = params["columns"];
    var defaultKeyIds = params["defaultKeyIds"];
    var sortOrder = params["sortOrder"] ? params["sortOrder"] : "asc";
    var singleSelect = params["singleSelect"] ? params["singleSelect"] : false;
    var uniqueId = params["uniqueId"] ? params["uniqueId"] : "id";
    var rememberSelected = params["rememberSelected"] ? params["rememberSelected"] : false;
    var oneCheckedClassName = params["oneCheckedClassName"] ? params["oneCheckedClassName"] : {edit: 'a#edit'}; // 长度为1，按钮为选择中状态的，class名称组合 如： "a#del a#pass a#unpas"
    var moreCheckedClassName = params["moreCheckedClassName"] ? params["moreCheckedClassName"] : {del: 'a#del'}; // 长度>0，按钮为选择中状态的，class名称组合 如： "a#del a#pass a#unpas"
    var oTableInit = {};
    //初始化Table
    oTableInit.Init = function () {
        $(id).bootstrapTable({
            url: baseURL + url,                     //请求后台的URL（*）
            method: 'get',                          //请求方式（*）
            toolbar: toolbarId,                     //工具按钮用哪个容器
            cache: false,                           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                       //是否显示分页（*）
            sortable: true,                         //是否启用排序
            sortOrder: sortOrder,                   //排序方式(针对序号)
            sortName: "id",                         //排序字段
            queryParams: oTableInit.queryParams,    //传递参数（*）
            sidePagination: "server",               //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                          //初始化加载第一页，默认第一页
            limit: 10,                           //每页的记录行数（*）
            pageList: [10, 20, 50, 100, 500],                 //可供选择的每页的行数（*）
            search: false,                           //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大 true显示,false不显示
            strictSearch: false,                     //严格搜索
            paginationPreText: "上一页",             //示的图标或文本，即上一个按钮
            paginationNextText: "下一页",            //显示的图标或文本，即下一个按钮
            queryParamsType: "",
            striped: false,                         //隔行变色
            showColumns: true,                      //是否显示所有的列
            showRefresh: true,                      //是否显示刷新按钮
            minimumCountColumns: 1,                 //最少允许的列数
            clickToSelect: true,                    //是否启用点击选中行
            uniqueId: uniqueId,                     //每一行的唯一标识，一般为主键列
            showToggle: true,                       //是否显示详细视图和列表视图的切换按钮
            cardView: false,                        //是否显示详细视图
            detailView: false,                      //是否显示父子表
            singleSelect: singleSelect,             //设置为true将禁止多选
            maintainSelected: true,                  //客户端分页，这个设为 true 翻页后已经选中的复选框不会丢失
            rememberSelected: rememberSelected,     //后端分页(自定义的) 翻页保存已选数据 true保存 false不保存
            responseHandler: function (res) {       //res 从服务器为获得的数据
                //翻页是否保留选中数据
                //var rememberSelected = $(id).bootstrapTable('getOptions').rememberSelected;
                //当前表格的行数据的唯一标识符
                //var uniqueId = $(id).bootstrapTable('getOptions').uniqueId;
                if (res.code == 200) {
                    var data = res.data.list;
                    var total = res.data.total;
                    if (rememberSelected) {
                        //开启 翻页保留已选数据
                        $.each(data, function (i, row) {
                            //state:设置复选框：field: 'state', checkbox: true,
                            row.state = $.inArray(row[uniqueId], rememberSelectedIds[id.substring(1)]) !== -1;
                        });
                    }
                    return {rows: data, total: total};
                } else {
                    $.modal.msgWarning(res.msg);
                    return {rows: [], total: 0};
                }
            },
            columns: columns,
            //加载成功时执行
            onLoadSuccess: function () {
                if (rememberSelected) {
                    if (rememberSelectedRows[id.substring(1)] && rememberSelectedIds[id.substring(1)]) {
                        let isOne = rememberSelectedIds[id.substring(1)].length != 1;
                        let isMore = !rememberSelectedIds[id.substring(1)].length;
                        setBtnOneChecked(oneCheckedClassName, isOne, moreCheckedClassName, isMore);
                        tableCallBack(rememberSelectedRows, rememberSelectedRows);
                    } else {
                        setBtnOneChecked(oneCheckedClassName, true, moreCheckedClassName, true);
                        tableCallBack(null, null);
                    }
                } else {
                    var rs = $(id).bootstrapTable('getSelections');
                    setBtnOneChecked(oneCheckedClassName, rs.length != 1, moreCheckedClassName, !rs.length);
                    tableCallBack(rs, rs);
                }
            },
            //加载失败时执行
            onLoadError: function () {
            },
            //选中行事件 row	选中行的数据对象json格式如{id:1,playe:true}  $element被点击的jquery对象
            onCheck: function (row, $element) {
                func = 'union';
                funcs = 'unions';
                //翻页是否保留选中数据
                //var rememberSelected = $(id).bootstrapTable('getOptions').rememberSelected;
                //当前表格的唯一标识符
                //var uniqueId = $(id).bootstrapTable('getOptions').uniqueId;
                var datas = $.isArray($element) ? $element : [$element];
                var rowIds;
                if ($.isArray(row)) {
                    rowIds = $.map(row, function (r) {
                        return r[uniqueId];
                    });
                } else {
                    rowIds = [row[uniqueId]];
                }
                var rowa = $.map(!$.isArray(row) ? [row] : row, function (r) {
                    return r;
                });
                if (rememberSelected) {
                    var selectedIds = rememberSelectedIds[id.substring(1)];
                    if ($.common.isNotEmpty(selectedIds)) {
                        rememberSelectedIds[id.substring(1)] = _[func](selectedIds, rowIds);
                    } else {
                        rememberSelectedIds[id.substring(1)] = _[func]([], rowIds);
                    }
                    var selectedRows = rememberSelectedRows[id.substring(1)];
                    if ($.common.isNotEmpty(selectedRows)) {
                        rememberSelectedRows[id.substring(1)] = aa[funcs](selectedRows, rowa, uniqueId);
                    } else {
                        rememberSelectedRows[id.substring(1)] = aa[funcs]([], rowa, uniqueId);
                    }
                    let isOne = rememberSelectedIds[id.substring(1)].length != 1;
                    let isMore = !rememberSelectedIds[id.substring(1)].length;
                    setBtnOneChecked(oneCheckedClassName, isOne, moreCheckedClassName, isMore);
                    // $(oneCheckedClassName).toggleClass('disabled', rememberSelectedIds[id.substring(1)].length != 1);
                    // $(moreCheckedClassName).toggleClass('disabled', !rememberSelectedIds[id.substring(1)].length);
                    tableCallBack(datas, rememberSelectedRows);
                } else {
                    var rs = $(id).bootstrapTable('getSelections');
                    // $(oneCheckedClassName).toggleClass('disabled', rs.length != 1);
                    // $(moreCheckedClassName).toggleClass('disabled', !rs.length);
                    setBtnOneChecked(oneCheckedClassName, rs.length != 1, moreCheckedClassName, !rs.length);
                    tableCallBack(datas, rs);
                }
            },
            //取消选中行事件 row	选中行的数据对象json格式如{id:1,playe:true}  $element被点击的jquery对象
            onUncheck: function (row, $element) {
                func = 'difference';
                funcs = 'differences';
                //翻页是否保留选中数据
                //var rememberSelected = $(id).bootstrapTable('getOptions').rememberSelected;
                //当前表格的唯一标识符
                //var uniqueId = $(id).bootstrapTable('getOptions').uniqueId;
                var datas = $.isArray($element) ? $element : [$element];
                var rowIds;
                if ($.isArray(row)) {
                    rowIds = $.map(row, function (r) {
                        return r[uniqueId];
                    });
                } else {
                    rowIds = [row[uniqueId]];
                }
                var rowa = $.map(!$.isArray(row) ? [row] : row, function (r) {
                    return r;
                });
                if (rememberSelected) {
                    var selectedIds = rememberSelectedIds[id.substring(1)];
                    if ($.common.isNotEmpty(selectedIds)) {
                        rememberSelectedIds[id.substring(1)] = _[func](selectedIds, rowIds);
                    } else {
                        rememberSelectedIds[id.substring(1)] = _[func]([], rowIds);
                    }
                    var selectedRows = rememberSelectedRows[id.substring(1)];
                    if ($.common.isNotEmpty(selectedRows)) {
                        rememberSelectedRows[id.substring(1)] = aa[funcs](selectedRows, rowa, uniqueId);
                    } else {
                        rememberSelectedRows[id.substring(1)] = aa[funcs]([], rowa, uniqueId);
                    }
                    let isOne = rememberSelectedIds[id.substring(1)].length != 1;
                    let isMore = !rememberSelectedIds[id.substring(1)].length;
                    setBtnOneChecked(oneCheckedClassName, isOne, moreCheckedClassName, isMore);
                    // $(oneCheckedClassName).toggleClass('disabled', rememberSelectedIds[id.substring(1)].length != 1);
                    // $(moreCheckedClassName).toggleClass('disabled', !rememberSelectedIds[id.substring(1)].length);
                    //rememberSelectedRows[id.substring(1)] 页面只有一个表格时 可以这么写 则在回调方法中获取当前表格所有选择对象数组大小：row.length
                    //rememberSelectedRows 页面有多个表格时 需这么写 则在回调方法中获取当前表格所有选择对象数组大小：row.表格id.length
                    tableCallBack(datas, rememberSelectedRows);
                } else {
                    var rs = $(id).bootstrapTable('getSelections');
                    // $(oneCheckedClassName).toggleClass('disabled', rs.length != 1);
                    // $(moreCheckedClassName).toggleClass('disabled', !rs.length);
                    setBtnOneChecked(oneCheckedClassName, rs.length != 1, moreCheckedClassName, !rs.length);
                    tableCallBack(datas, rs);
                }
            },
            //全选中事件  rowsAfter数组，返回全选后全部选中的数据  rowsBefore全选前的数据，在1.41之前此参数无效,1.52之后有效
            onCheckAll: function (rowsAfter, rowsBefore) {
                func = 'union';
                funcs = 'unions';
                var datas = $.isArray(rowsAfter) ? rowsAfter : [rowsAfter];
                var rowIds;
                if ($.isArray(rowsAfter)) {
                    rowIds = $.map(rowsAfter, function (r) {
                        return r[uniqueId];
                    });
                } else {
                    rowIds = [rowsAfter[uniqueId]];
                }
                var rowa = $.map(!$.isArray(rowsAfter) ? [rowsAfter] : rowsAfter, function (r) {
                    return r;
                });
                if (rememberSelected) {
                    var selectedIds = rememberSelectedIds[id.substring(1)];
                    if ($.common.isNotEmpty(selectedIds)) {
                        rememberSelectedIds[id.substring(1)] = _[func](selectedIds, rowIds);
                    } else {
                        rememberSelectedIds[id.substring(1)] = _[func]([], rowIds);
                    }
                    var selectedRows = rememberSelectedRows[id.substring(1)];
                    if ($.common.isNotEmpty(selectedRows)) {
                        rememberSelectedRows[id.substring(1)] = aa[funcs](selectedRows, rowa, uniqueId);
                    } else {
                        rememberSelectedRows[id.substring(1)] = aa[funcs]([], rowa, uniqueId);
                    }
                    let isOne = rememberSelectedIds[id.substring(1)].length != 1;
                    let isMore = !rememberSelectedIds[id.substring(1)].length;
                    setBtnOneChecked(oneCheckedClassName, isOne, moreCheckedClassName, isMore);
                    // $(oneCheckedClassName).toggleClass('disabled', rememberSelectedIds[id.substring(1)].length != 1);
                    // $(moreCheckedClassName).toggleClass('disabled', !rememberSelectedIds[id.substring(1)].length);
                    tableCallBack(datas, rememberSelectedRows);
                } else {
                    var rs = $(id).bootstrapTable('getSelections');
                    // $(oneCheckedClassName).toggleClass('disabled', rs.length != 1);
                    // $(moreCheckedClassName).toggleClass('disabled', !rs.length);
                    setBtnOneChecked(oneCheckedClassName, rs.length != 1, moreCheckedClassName, !rs.length);
                    tableCallBack(datas, rs);
                }
            },
            // 取消全选中事件 rowsAfter取消全选后全部选中的数据 rowsBefore取消全选前的数据，在1.41之前此参数无效,1.52之后有效
            onUncheckAll: function (rowsAfter, rowsBefore) {
                func = 'difference';
                funcs = 'differences';
                var datas = $.isArray(rowsBefore) ? rowsBefore : [rowsBefore];
                var rowIds;
                if ($.isArray(rowsBefore)) {
                    rowIds = $.map(rowsBefore, function (r) {
                        return r[uniqueId];
                    });
                } else {
                    rowIds = [rowsBefore[uniqueId]];
                }
                var rowa = $.map(!$.isArray(rowsBefore) ? [rowsBefore] : rowsBefore, function (r) {
                    return r;
                });
                if (rememberSelected) {
                    var selectedIds = rememberSelectedIds[id.substring(1)];
                    if ($.common.isNotEmpty(selectedIds)) {
                        rememberSelectedIds[id.substring(1)] = _[func](selectedIds, rowIds);
                    } else {
                        rememberSelectedIds[id.substring(1)] = _[func]([], rowIds);
                    }
                    var selectedRows = rememberSelectedRows[id.substring(1)];
                    if ($.common.isNotEmpty(selectedRows)) {
                        rememberSelectedRows[id.substring(1)] = aa[funcs](selectedRows, rowa, uniqueId);
                    } else {
                        rememberSelectedRows[id.substring(1)] = aa[funcs]([], rowa, uniqueId);
                    }
                    let isOne = rememberSelectedIds[id.substring(1)].length != 1;
                    let isMore = !rememberSelectedIds[id.substring(1)].length;
                    setBtnOneChecked(oneCheckedClassName, isOne, moreCheckedClassName, isMore);
                    // $(oneCheckedClassName).toggleClass('disabled', rememberSelectedIds[id.substring(1)].length != 1);
                    // $(moreCheckedClassName).toggleClass('disabled', !rememberSelectedIds[id.substring(1)].length);
                    tableCallBack(datas, rememberSelectedRows);
                } else {
                    var rs = $(id).bootstrapTable('getSelections');
                    // $(oneCheckedClassName).toggleClass('disabled', rs.length != 1);
                    // $(moreCheckedClassName).toggleClass('disabled', !rs.length);
                    setBtnOneChecked(oneCheckedClassName, rs.length != 1, moreCheckedClassName, !rs.length);
                    tableCallBack(datas, rs);
                }
            },
            //刷新事件 params请求服务器参数 清空已选择数据
            onRefresh: function (params) {
                rememberSelectedIds[id.substring(1)] = [];
                rememberSelectedRows[id.substring(1)] = [];
            }
        });
    };
    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            order: params.sortOrder,               //序号排序
            page: params.pageNumber,               //页码 默认是1
            limit: params.limit                 //页面大小
            //sortName: params.sortName                //排序字段
        };
        if (defaultKeyIds.length !== 0 && defaultKeyIds !== '') {
            $.each(defaultKeyIds, function (key) {
                if (defaultKeyIds[key]) {
                    var value = defaultKeyIds[key].toString();
                    //输入框数据
                    if (value.startsWith("#") || value.startsWith(".")) {
                        //#id 选择器 或 .class 选择器
                        temp[key] = $(defaultKeyIds[key]).val();
                    } else {
                        temp[key] = value;
                    }
                } else {
                    temp[key] = defaultKeyIds[key];
                }
            });
        }
        // 多选框 -数据是数组格式
        var checkboxValue = [];
        $('input[type="checkbox"][data-table=' + id.substring(1) + ']:checked').each(function () {
            checkboxValue.push($(this).val());
        });
        if (checkboxValue.length > 0) {
            temp["checkBoxValue"] = JSON.stringify(checkboxValue);
        }
        // 单选框 -数据是数组格式
        var radioValue = [];
        $('input[type="radio"][data-table=' + id.substring(1) + ']:checked').each(function () {
            radioValue.push($(this).val());
        });
        if (radioValue.length > 0) {
            temp["radioValue"] = JSON.stringify(radioValue);
        }
        return temp;
    };
    return oTableInit;
};

function setBtnOneChecked(oneCheckedClassName, isOne, moreCheckedClassName, isMore) {
    $.each(oneCheckedClassName, function (key) {
        $(oneCheckedClassName[key]).toggleClass('disabled', isOne);
    });
    $.each(moreCheckedClassName, function (key) {
        $(moreCheckedClassName[key]).toggleClass('disabled', isMore);
    });
}

/**
 * 表格销毁
 * @param tableId         表格#id
 */
function tableDestroy(tableId) {
    $(tableId).bootstrapTable('destroy');
}

/**
 * 刷新 带搜索参数 不带分页参数 从第一页开始
 * @param id         表格#id
 */
function tableRefresh(id) {
    $(id).bootstrapTable('refreshOptions', {pageNumber: 1});
}

/**
 * 刷新 带搜索参数 和 分页参数
 * @param id         表格#id
 */
function tableRefreshParams(id) {
    $(id).bootstrapTable('refresh');
}

/**
 * 根据 uniqueId 获取行数据 getRowByUniqueId
 */
function tableGetRowByUniqueId(bootstrapTableId, uniqueId) {
    return $(bootstrapTableId).bootstrapTable('getRowByUniqueId', uniqueId);
}

/**
 * 返回所选的行，当没有选择任何行的时候返回一个空数组 getSelections
 */
function tableGetSelections(bootstrapTableId) {
    return $(bootstrapTableId).bootstrapTable("getSelections");
}

/**
 * 默认选中
 * 按值数组检查一行，参数包含：
 * tableId:表格id 包含 #
 * field：用于查找记录的字段的名称
 * values：要检查的行的值数组 数组值得类型要与字段值类型一样 数字类型用parseInt()转换
 * 例:$(“#table”).bootstrapTable(“checkBy”, {field:”field_name”, values:[“value1”,”value2”,”value3”]})
 */
function checkByField(tableId, field, values) {
    // var carIds = getValueByUrl("carIds").split(",");
    // var carIdValue = [];
    // for (var i = 0; i < carIds.length; i++) {
    //     carIdValue.push(parseInt(carIds[i]));
    // }
    //必须在表格加载完成后才可检索数据
    $(tableId).on('load-success.bs.table', function (data) {
        //设置默认值方法 根据字段 id 以及对应的值 默认选中行
        $(tableId).bootstrapTable("checkBy", {field: field, values: values});
    });
}

/**
 * 列显示
 * @param tableId
 * @param columnName
 */
//显示列 tableId:#teble   columnName:列名
function showColumn(tableId, columnName) {
    $(tableId).bootstrapTable('showColumn', columnName);
}

/**
 * 列隐藏
 * tableId:表格id   columnName:列名
 */
function hideColumn(tableId, columnName) {
    $(tableId).bootstrapTable('hideColumn', columnName);
}


// 搜索框等数据改变时候 执行搜索操作
$('input').on('input propertychange', function () {
    setChangeEvent($(this));
});

$('select').on('change', function () {
    setChangeEvent($(this));
});

$('checkbox').on('change', function () {
    setChangeEvent($(this));
});

function setChangeEvent(object) {
    var ids = object.attr("data-table");
    // 存在此属性
    if (ids && typeof ids !== typeof undefined && ids !== false) {
        ids = ids.split(',');
        for (var i = 0; i < ids.length; i++) {
            tableRefresh("#" + ids[i]);
        }
    } else {
        return false;
    }
}

//选中事件操作数组 针对唯一标识符
var union = function (array, ids) {
    $.each(ids, function (i, id) {
        if ($.inArray(id, array) == -1) {
            array[array.length] = id;
        }
    });
    return array;
};
//取消选中事件操作数组  针对唯一标识符
var difference = function (array, ids) {
    $.each(ids, function (i, id) {
        var index = $.inArray(id, array);
        if (index != -1) {
            array.splice(index, 1);
        }
    });
    return array;
};
//选中事件操作数组 针对数据对象  uniqueId唯一标识
var unions = function (arrays, rowa, uniqueId) {
    $.each(rowa, function (i, row) {
        if ($.inArray(row, arrays) == -1) {
            var j = 0;
            $.each(arrays, function (k, array) {
                if (array[uniqueId] == row[uniqueId]) {
                    j = 1;
                    return false;
                }
            });
            if (j == 0) {
                arrays[arrays.length] = row;
            }
        }
    });
    return arrays;
};
//取消选中事件操作数组  针对数据对象  uniqueId唯一标识
var differences = function (arrays, rowa, uniqueId) {
    $.each(rowa, function (i, row) {
        $.each(arrays, function (k, array) {
            if (array[uniqueId] == row[uniqueId]) {
                arrays.splice(k, 1);
                return false;
            }
        });
    });
    return arrays;
};
var _ = {"union": union, "difference": difference};
var aa = {"unions": unions, "differences": differences};
