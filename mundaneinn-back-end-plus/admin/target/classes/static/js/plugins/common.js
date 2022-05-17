/** js扩展*************************************************************************************************************/
var baseURL = '../../';
// 弹窗状态码
modal_status = {
    SUCCESS: "success",
    FAIL: "error",
    WARNING: "warning"
};
// js扩展
(function ($) {
    $.extend({
        // 表单封装处理
        form: {
            // 表单重置
            reset: function (formId) {
                var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                $(currentId)[0].reset();
            },
            // 获取选中复选框项
            selectCheckeds: function (name) {
                var checkeds = "";
                $('input:checkbox[name="' + name + '"]:checked').each(function (i) {
                    if (0 == i) {
                        checkeds = $(this).val();
                    } else {
                        checkeds += ("," + $(this).val());
                    }
                });
                return checkeds;
            },
            // 获取选中下拉框项
            selectSelects: function (name) {
                var selects = "";
                $('#' + name + ' option:selected').each(function (i) {
                    if (0 == i) {
                        selects = $(this).val();
                    } else {
                        selects += ("," + $(this).val());
                    }
                });
                return selects;
            }
        },
        // 操作封装处理--非首页页面可以使用()
        operate: {
            // post上传文件
            postFile: function (formData, callBack) {
                $.ajax({
                    url: baseURL + "common/upload",
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    //async: false,//false同步请求  true异步
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            //返回对象 文件名及文件路径
                            callBack(result);
                        } else {
                            $.modal.msgError(result.msg);
                        }
                        $.modal.closeLoading();
                    },
                    error: function (result) {
                        $.modal.closeLoading();
                        $.modal.msgError(result.msg);
                    }
                });
            },
            // post删除文件 data格式：key-value: url:  (多个文件逗号隔开)
            deleteFile: function (data, callBack) {
                $.ajax({
                    url: baseURL + "common/delete",
                    type: 'POST',
                    data: data,
                    traditional: true,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            callBack(result);
                        } else {
                            $.modal.msgError(result.msg);
                        }
                        $.modal.closeLoading();
                    },
                    error: function (result) {
                        $.modal.closeLoading();
                        $.modal.msgError(result.msg);
                        callBack("");
                    }
                });
            },
            // get请求传输
            get: function (url, callback) {
                $.operate.submit(url, "get", "json", "", callback);
            },
            //-- 非首页页面可以使用-- 且后台使用 单个数据接收或map方式接收(无法使用实体接收) ******************/
            // 提交数据
            submit: function (url, type, dataType, data, callback) {
                var config = {
                    url: baseURL + url,
                    type: type,
                    traditional: true,
                    dataType: dataType,
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
                    },
                    success: function (result) {
                        if (typeof callback == "function") {
                            if (result.code == 200) {
                                callback(result);
                            } else {
                                $.modal.msgError(result.msg);
                            }
                        }
                        $.modal.closeLoading();
                    },
                    error: function (result) {
                        $.modal.closeLoading();
                        $.modal.msgError(result.msg);
                        callback("");
                    }
                };
                $.ajax(config)
            },
            // post请求传输
            post: function (url, data, callback) {
                if (!$.modal.lock) {
                    $.modal.lock = true;
                    $.operate.submit(url, "post", "json", data, callback);
                }
            },
            // 实体接收
            submitEntity: function (url, type, data, callback) {
                var config = {
                    url: baseURL + url,
                    type: type,
                    contentType: "application/json;charset=UTF-8",
                    //async: false,   //false同步 默认异步
                    data: JSON.stringify(data),
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
                    },
                    success: function (result) {
                        if (typeof callback == "function") {
                            if (result.code == 200) {
                                callback(result);
                            } else {
                                $.modal.msgError(result.msg);
                            }
                        }
                        $.modal.closeLoading();
                    },
                    error: function (result) {
                        $.modal.closeLoading();
                        $.modal.msgError(result.msg);
                        callback("");
                    }
                };
                $.ajax(config)
            },
            // post 请求传输 实体接收
            postEntity: function (url, data, callback) {
                if (!$.modal.lock) {
                    $.modal.lock = true;
                    $.operate.submitEntity(url, "post", data, callback);
                }
            }
        },
        // 弹出层封装处理
        modal: {
            lock: false, //默认不上锁,防止按钮快速点击的事件导致发送多个请求
            // 显示图标
            icon: function (type) {
                var icon = "";
                if (type == modal_status.WARNING) {
                    icon = 0;
                } else if (type == modal_status.SUCCESS) {
                    icon = 1;
                } else if (type == modal_status.FAIL) {
                    icon = 2;
                } else {
                    icon = 3;
                }
                return icon;
            },
            // 消息提示
            msg: function (content, type) {
                if (type != undefined) {
                    layer.msg(content, {icon: $.modal.icon(type), time: 1000, shift: 5});
                } else {
                    layer.msg(content);
                }
            },
            // 错误消息
            msgError: function (content) {
                $.modal.lock = false;
                $.modal.msg(content, modal_status.FAIL);
            },
            // 成功消息
            msgSuccess: function (content) {
                $.modal.msg(content, modal_status.SUCCESS);
            },
            // 警告消息
            msgWarning: function (content) {
                $.modal.msg(content, modal_status.WARNING);
            },
            // 消息提示 延时操作
            msgDelay: function (msg, type, callback) {
                layer.msg(msg, {
                        icon: $.modal.icon(type),
                        time: 500,
                        shade: [0.1, '#8F8F8F']
                    },
                    function () {
                        callback(true);
                    });
            },
            // 错误消息
            msgErrorDelay: function (content, callback) {
                $.modal.lock = false;
                $.modal.msgDelay(content, modal_status.FAIL, callback);
            },
            // 成功消息
            msgSuccessDelay: function (content, callback) {
                $.modal.msgDelay(content, modal_status.SUCCESS, callback);
            },
            // 警告消息
            msgWarningDelay: function (content, callback) {
                $.modal.msgDelay(content, modal_status.WARNING, callback);
            },
            // 消息提示并刷新父窗体
            msgReload: function (msg, type) {
                layer.msg(msg, {
                        icon: $.modal.icon(type),
                        time: 500,
                        shade: [0.1, '#8F8F8F']
                    },
                    function () {
                        $.modal.lock = false;
                        $.modal.reload();
                    });
            },
            // 弹出提示
            alert: function (content, type) {
                layer.alert(content, {
                    icon: $.modal.icon(type),
                    title: "系统提示",
                    btn: ['确认'],
                    btnclass: ['btn btn-primary']
                });
            },
            // 错误提示
            alertError: function (content) {
                $.modal.alert(content, modal_status.FAIL);
            },
            // 成功提示
            alertSuccess: function (content) {
                $.modal.alert(content, modal_status.SUCCESS);
            },
            // 警告提示
            alertWarning: function (content) {
                $.modal.alert(content, modal_status.WARNING);
            },
            // 关闭窗体-不稳定-如果使用需要在所有逻辑处理后再调用
            close: function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },
            // 关闭全部窗体-不稳定-如果使用需要在所有逻辑处理后再调用
            closeAll: function () {
                parent.layer.closeAll();
                layer.closeAll();
            },
            // 确认窗体
            confirm: function (content, callBack) {
                layer.confirm(content, {
                    icon: 3,
                    title: "系统提示",
                    btn: ['取消', '确认']
                }, function (index) {
                    //取消
                    layer.close(index);
                }, function (index) {
                    //确定
                    layer.close(index);
                    callBack(true);
                });
            },
            // 弹出层全部操作 yesCallback为右下角确定按钮回调 cancelCallback为右下角取消按钮回调
            open: function (title, url, confirmButton, closeButton, isFull, width, height, yesCallback, cancelCallback) {
                //如果是移动端，就使用自适应大小弹窗
                if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 10);
                }
                var type;
                // 传 url = "#test"
                if (url.indexOf("#") != -1) {
                    url = $(url);
                    type = 1;
                } else if (url.indexOf(".html") != -1) {  //传url= '.html'
                    type = 2;
                } else {
                    //传url 为字符串 或者任意的文本或html
                    type = 1;
                }
                var index = layer.open({
                    type: type,
                    area: [width + 'px', height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: [title, 'font-weight: bold;'],//设置标题样式
                    content: url,
                    //左边取消 右边确定
                    btn: $.common.isEmpty(confirmButton) && $.common.isEmpty(closeButton) ? [] : $.common.isEmpty(confirmButton) && !$.common.isEmpty(closeButton) ? [closeButton] : !$.common.isEmpty(confirmButton) && $.common.isEmpty(closeButton) ? [confirmButton] : [closeButton, confirmButton],
                    // 弹层外区域关闭
                    shadeClose: false,
                    success: function (layero, index) {
                        //弹出后的成功回调方法 添加自定义确定按钮 onclick='layerConfirmButton("+layero+","+index+")'
                        //$("<a class=\"layui-layer-btn1\">确定</a>").appendTo("body .layui-layer-btn");
                    },
                    yes: function (index, layero) {
                        //右下角 从左到右 第一个按钮回调 默认取消按钮
                        //存在取消按钮
                        if (!$.common.isEmpty(closeButton)) {
                            if (!cancelCallback) {
                                //无回调函数 直接关闭
                                layer.close(index);
                                return false;
                            } else if ($.common.isNotEmpty(cancelCallback) && typeof cancelCallback == "function") {
                                //本页面直接回调
                                cancelCallback(index, layero);
                            } else {
                                //另一个页面 回调
                                var iframeWin = layero.find('iframe')[0];
                                iframeWin.contentWindow.cancelHandler(index, layero);
                            }
                        } else {
                            //没有取消按钮 只有确定按钮
                            if ($.common.isEmpty(closeButton) && !$.common.isEmpty(confirmButton)) {
                                if ($.common.isNotEmpty(yesCallback) && typeof yesCallback == "function") {
                                    //本页面直接回调
                                    //先简单判断页面必填选项 class="required"
                                    var errorCount = 0;
                                    $(".required").each(function () {
                                        if (!$(this).val()) {
                                            $(this).addClass("input-error");
                                            errorCount++;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        $.modal.msgWarning("请先完善数据后再提交");
                                        return false;
                                    }
                                    //简单校验 电话格式 class="phone"
                                    $(".phone").each(function () {
                                        var phone = $(this).val();
                                        if (phone && !regPhone.test(phone)) {
                                            $(this).addClass("input-error");
                                            //name 属性值为提示语  name="电话号码"  由于提示内容不固定 按实际情况处理
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    //简单校验 邮箱格式 class="email"
                                    $(".email").each(function () {
                                        var email = $(this).val();
                                        if (email && !regEmail.test(email)) {
                                            $(this).addClass("input-error");
                                            //name 属性值为提示语  name="电话号码"  由于提示内容不固定 按实际情况处理
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    //简单校验 金额格式 class="money" 可以为0 小数点后两位
                                    $(".money").each(function () {
                                        var money = $(this).val();
                                        if (money && !regMoney2.test(money)) {
                                            $(this).addClass("input-error");
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    //简单校验 金额格式 class="money2" 不可以为0 小数点后两位
                                    $(".money2").each(function () {
                                        var money = $(this).val();
                                        if (money && !regMoney.test(money)) {
                                            $(this).addClass("input-error");
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    //简单校验 数字格式 class="number" 零和非零开头的整数
                                    $(".number").each(function () {
                                        var number = $(this).val();
                                        if (number && !regNumber2.test(number)) {
                                            $(this).addClass("input-error");
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    yesCallback(index, layero);
                                } else {
                                    //另一个页面 回调
                                    var iframeWin = layero.find('iframe')[0];
                                    var html = iframeWin.contentWindow;
                                    var errorCount = 0;
                                    html.$(".required").each(function () {
                                        if (!$(this).val()) {
                                            $(this).addClass("input-error");
                                            errorCount++;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        $.modal.msgWarning("请先完善数据后再提交");
                                        return false;
                                    }
                                    //简单校验 电话格式 class="phone" 11位
                                    html.$(".phone").each(function () {
                                        var phone = $(this).val();
                                        if (phone && !regPhone.test(phone)) {
                                            $(this).addClass("input-error");
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    //简单校验 邮箱格式 class="email"
                                    html.$(".email").each(function () {
                                        var email = $(this).val();
                                        if (email && !regEmail.test(email)) {
                                            $(this).addClass("input-error");
                                            //name 属性值为提示语  name="电话号码"  由于提示内容不固定 按实际情况处理
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    //简单校验 金额格式 class="money" 可以为0 小数点后两位
                                    html.$(".money").each(function () {
                                        var money = $(this).val();
                                        if (money && !regMoney2.test(money)) {
                                            $(this).addClass("input-error");
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    //简单校验 金额格式 class="money2" 不可以为0 小数点后两位
                                    html.$(".money2").each(function () {
                                        var money = $(this).val();
                                        if (money && !regMoney.test(money)) {
                                            $(this).addClass("input-error");
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    //简单校验 数字格式 class="number" >=0 的整数
                                    html.$(".number").each(function () {
                                        var number = $(this).val();
                                        if (number && !regNumber2.test(number)) {
                                            $(this).addClass("input-error");
                                            $.modal.msgWarning($(this).attr("name") + "格式错误");
                                            errorCount++;
                                            return false;
                                        } else {
                                            $(this).removeClass("input-error");
                                        }
                                    });
                                    if (errorCount != 0) {
                                        return false;
                                    }
                                    html.submitHandler(index, layero);
                                }
                            }
                        }
                    },
                    btn2: function (index, layero) {
                        //右下角 从左到右 第二个按钮回调
                        //确定按钮
                        if (!$.common.isEmpty(confirmButton)) {
                            if ($.common.isNotEmpty(yesCallback) && typeof yesCallback == "function") {
                                //本页面直接回调
                                var iframeWin = layero.find('iframe')[0];
                                var html = iframeWin.contentWindow;
                                //先简单判断页面必填选项 class="required"
                                var errorCount = 0;
                                html.$(".required").each(function () {
                                    if (!$(this).val()) {
                                        $(this).addClass("input-error");
                                        errorCount++;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    $.modal.msgWarning("请先完善数据后再提交");
                                    return false;
                                }
                                //简单校验 电话格式 class="phone"
                                html.$(".phone").each(function () {
                                    var phone = $(this).val();
                                    if (phone && !regPhone.test(phone)) {
                                        $(this).addClass("input-error");
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }
                                //简单校验 邮箱格式 class="email"
                                html.$(".email").each(function () {
                                    var email = $(this).val();
                                    if (email && !regEmail.test(email)) {
                                        $(this).addClass("input-error");
                                        //name 属性值为提示语  name="电话号码"  由于提示内容不固定 按实际情况处理
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }
                                //简单校验 金额格式 class="money" 可以为0
                                html.$(".money").each(function () {
                                    var money = $(this).val();
                                    if (money && !regMoney2.test(money)) {
                                        $(this).addClass("input-error");
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }
                                //简单校验 金额格式 class="money2" 不可以为0 小数点后两位
                                html.$(".money2").each(function () {
                                    var money = $(this).val();
                                    if (money && !regMoney.test(money)) {
                                        $(this).addClass("input-error");
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }
                                //简单校验 数字格式 class="number"
                                html.$(".number").each(function () {
                                    var number = $(this).val();
                                    if (number && !regNumber2.test(number)) {
                                        $(this).addClass("input-error");
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }
                                yesCallback(index, layero);
                                return false;
                            } else {
                                //另一个页面 回调
                                var iframeWin = layero.find('iframe')[0];
                                var html = iframeWin.contentWindow;
                                var errorCount = 0;
                                html.$(".required").each(function () {
                                    if (!$(this).val()) {
                                        $(this).addClass("input-error");
                                        errorCount++;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    $.modal.msgWarning("请先完善数据后再提交");
                                    return false;
                                }
                                //简单校验 电话格式 class="phone"
                                html.$(".phone").each(function () {
                                    var phone = $(this).val();
                                    if (phone && !regPhone.test(phone)) {
                                        $(this).addClass("input-error");
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }
                                //简单校验 邮箱格式 class="email"
                                html.$(".email").each(function () {
                                    var email = $(this).val();
                                    if (email && !regEmail.test(email)) {
                                        $(this).addClass("input-error");
                                        //name 属性值为提示语  name="电话号码"  由于提示内容不固定 按实际情况处理
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }
                                //简单校验 金额格式 class="money" 可以为0
                                html.$(".money").each(function () {
                                    var money = $(this).val();
                                    if (money && !regMoney2.test(money)) {
                                        $(this).addClass("input-error");
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }
                                //简单校验 金额格式 class="money2" 不可以为0 小数点后两位
                                html.$(".money2").each(function () {
                                    var money = $(this).val();
                                    if (money && !regMoney.test(money)) {
                                        $(this).addClass("input-error");
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }
                                //简单校验 数字格式 class="number"
                                html.$(".number").each(function () {
                                    var number = $(this).val();
                                    if (number && !regNumber2.test(number)) {
                                        $(this).addClass("input-error");
                                        $.modal.msgWarning($(this).attr("name") + "格式错误");
                                        errorCount++;
                                        return false;
                                    } else {
                                        $(this).removeClass("input-error");
                                    }
                                });
                                if (errorCount != 0) {
                                    return false;
                                }

                                html.submitHandler(index, layero);
                                return false; //禁止点击该按钮关闭
                            }
                        }
                    },
                    cancel: function (index, layero) {
                        //右上角关闭回调
                        $.modal.lock = false;
                        //无回调函数 直接关闭 return false开启该代码可禁止点击该按钮关闭
                        return true;
                    }
                });
                //true弹出全屏
                if (isFull) {
                    layer.full(index);
                }
            },

            //简单的输入层 默认只有一个输入框 多个需要自定义动态添加
            // formType:输入框类型，支持0（文本）1（密码）2（多行文本） 默认文本
            // value初始值(默认空) placeholder:输入框提示 htmlArray:动态添加输入框数组 confirmCallback:确定按钮回调 confirmButton:确定按钮名称(默认确定) closeButton:取消按钮名称(默认取消) width:弹窗宽 height:弹窗高
            //动态添加自定义的输入框示例：： var html = [];
            // html[0] = '  <input type="text" class="form-control " id="keyword" name="keyword" placeholder="请输入题库名称"\n' +
            //     '                                   data-table="bootstrapitemBankManagementTable"/>';
            // html[1] = '  <input type="text" class="form-control " id="keyword" name="keyword" placeholder="请输入题库名称"\n' +
            //     '                                   data-table="bootstrapitemBankManagementTable"/>';
            prompt: function (title, formType, value, placeholder, htmlArray, confirmCallback, confirmButton, closeButton, width, height) {
                var index = layer.prompt({
                        formType: formType ? formType : 0,
                        value: value ? value : "",
                        placeholder: placeholder ? placeholder : '',
                        title: title ? title : '请输入值',
                        btn: [confirmButton ? confirmButton : "取消", closeButton ? closeButton : "确定"],
                        area: [width ? width + "px" : '800px', height ? height + "px" : '350px'], //自定义文本域宽高
                        btn2: function (index, elem) {
                            //确定
                            // 得到输入的数据，这里采用jquery，当然可以更换成layui内的jquery
                            var value = $('#layui-layer' + index + " .layui-layer-input").val();
                            if (!value) {
                                $('#layui-layer' + index + " .layui-layer-input").focus();
                                return false;　　// 如果value为空，不进入下一步
                            }
                            if ($.common.isNotEmpty(confirmCallback) && typeof confirmCallback == "function") {
                                //本页面直接回调  弹窗框的默认值 value  自定义文本框值自己获取
                                confirmCallback(index, value);
                            } else {
                                //另一个页面 回调
                                var iframeWin = layero.find('iframe')[0];
                                iframeWin.contentWindow.promptHandler(index, value);
                            }
                            return false;
                        }
                    }, function (value, index, elem) {
                        //取消
                        layer.close(index);
                    }
                );
                //自定义 内容 动态添加
                if (htmlArray && htmlArray.length !== 0 && htmlArray != '') {
                    for (var i = 0; i < htmlArray.length; i++) {
                        $(".layui-layer-input").parent().append("<br/>" + htmlArray[i]);
                    }
                }
            },
            // 打开遮罩层
            loading: function (message) {
                $.blockUI({message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>'});
            },
            // 关闭遮罩层
            closeLoading: function () {
                setTimeout(function () {
                    $.unblockUI();
                }, 50);
            },
            // 重新加载
            reload: function () {
                $.modal.lock = false;
                window.parent.location.reload();
            },
            // 重新加载当前页面
            reloadCurrPage: function () {
                $.modal.lock = false;
                window.location.reload();
            },
            // 弹窗显示图片
            photos: function (url) {
                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    area: ['550px', '400'],
                    skin: 'layui-layer', //没有背景色
                    shadeClose: true,
                    content: '<img  width="550" height="400" src=' + url + '>'
                });
            }
        },
        // 通用方法封装处理
        common: {
            // 判断字符串是否为空
            isEmpty: function (value) {
                if (value == null || this.trim(value) == "") {
                    return true;
                }
                return false;
            },
            // 判断一个字符串是否为非空串
            isNotEmpty: function (value) {
                return !$.common.isEmpty(value);
            },
            // 空对象转字符串
            nullToStr: function (value) {
                if ($.common.isEmpty(value)) {
                    return "-";
                }
                return value;
            },
            // 是否显示数据 为空默认为显示
            visible: function (value) {
                if ($.common.isEmpty(value) || value == true) {
                    return true;
                }
                return false;
            },
            // 空格截取
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            // 比较两个字符串（大小写敏感）
            equals: function (str, that) {
                return str == that;
            },
            // 比较两个字符串（大小写不敏感）
            equalsIgnoreCase: function (str, that) {
                return String(str).toUpperCase() === String(that).toUpperCase();
            },
            // 将字符串按指定字符分割
            split: function (str, sep, maxLen) {
                if ($.common.isEmpty(str)) {
                    return null;
                }
                var value = String(str).split(sep);
                return maxLen ? value.slice(0, maxLen - 1) : value;
            },
            // 字符串格式化(%s )
            sprintf: function (str) {
                var args = arguments, flag = true, i = 1;
                str = str.replace(/%s/g, function () {
                    var arg = args[i++];
                    if (typeof arg === 'undefined') {
                        flag = false;
                        return '';
                    }
                    return arg;
                });
                return flag ? str : '';
            },
            // 指定随机数返回
            random: function (min, max) {
                return Math.floor((Math.random() * max) + min);
            },
            // 判断字符串是否是以start开头
            startWith: function (value, start) {
                var reg = new RegExp("^" + start);
                return reg.test(value)
            },
            // 判断字符串是否是以end结尾
            endWith: function (value, end) {
                var reg = new RegExp(end + "$");
                return reg.test(value)
            },
            // 数组去重
            uniqueFn: function (array) {
                var result = [];
                var hashObj = {};
                for (var i = 0; i < array.length; i++) {
                    if (!hashObj[array[i]]) {
                        hashObj[array[i]] = true;
                        result.push(array[i]);
                    }
                }
                return result;
            },
            // 数组中的所有元素放入一个字符串
            join: function (array, separator) {
                if ($.common.isEmpty(array)) {
                    return null;
                }
                return array.join(separator);
            },
            // 获取form下所有的字段并转换为json对象
            formToJSON: function (formId) {
                var json = {};
                $.each($("#" + formId).serializeArray(), function (i, field) {
                    json[field.name] = field.value;
                });
                return json;
            },
            // 手机号正则匹配
            checkTel: function (tel) {
                var myreg = /^1\d{10}$/;//验证电话
                if (myreg.test(tel)) {
                    return true;
                } else {
                    return false;
                }
            },
            // 邮箱正则匹配
            checkEmail: function (email) {
                var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //验证邮箱
                if (reg.test(email)) {
                    return true;
                } else {
                    return false;
                }
            },
            // 金额匹配
            checkMoney: function (money) {
                var reg = regMoney;
                if (reg.test(money)) {
                    return true;
                } else {
                    return false;
                }
            },
            // 数字匹配
            checkNumber: function (number) {
                var reg = regNumber;
                if (reg.test(number)) {
                    return true;
                } else {
                    return false;
                }
            },
            // 日历 开始日期 - 结束日期  基本原则：开始日期 <= 结束日期
            // type为0:开始日期//结束日期最大值为当前日期; type为1: 结束日期无最大值限制
            /**
             * <li class="select-time">
             <label>打款确认时间： </label>
             <input type="text" class="form-control startTime-input" id="startTimeInput" placeholder="开始时间"
             name="startTimeInput" readonly data-table="bootstraptable-salesSupportList" style="width: 150px;background-color: #ffffff"/>
             <span>—</span>
             <input type="text" class="form-control  endTime-input" id="endTimeInput" placeholder="结束时间"
             name="endTimeInput" data-table="bootstraptable-salesSupportList" readonly style="width: 150px;background-color: #ffffff"/>
             </li>
             */
            dateInit: function (type) {
                //laydate.js
                if ($(".select-time").length > 0) {
                    var endDate = [];
                    var startDate = [];
                    // 日期控件底部按钮 默认:clear清空 now现在 confirm确定
                    var buttons = ["clear", "now", "confirm"];
                    //开始日期 class="startTime-input" name="startTimeInput"
                    $(".startTime-input").each(function (index) {
                        startDate[index] = laydate.render({
                            elem: this,
                            btns: buttons,
                            max: type === 0 ? new Date().format("yyyy-MM-dd") + " 23:59:59" : "2300-12-31 23:59:59",  //最大日期 当前日期
                            trigger: 'click', //采用click弹出
                            done: function (value, date) {
                                //时间选择后回调
                                if (value) {
                                    endDate[index].config.min.year = date.year;
                                    endDate[index].config.min.month = date.month - 1;
                                    endDate[index].config.min.date = date.date;
                                } else {
                                    endDate[index].config.min.year = '1800';
                                    endDate[index].config.min.month = '1';
                                    endDate[index].config.min.date = '1';
                                }
                                startDate[index].config.value = value;
                                // if (endDate[index].config.value && value) {
                                //     //触发表格刷新
                                //     setChangeEvent($("input[name='startTimeInput']"));
                                // }
                                if (value) {
                                    //触发表格刷新
                                    var object = $(this)[0].elem;
                                    setChangeEvent(object);
                                    //手动绑定change事件 解决添加readonly属性后 无法触发已绑定的click、change事件  $(this)[0].elem.trigger("change");
                                    $(object).change();
                                }
                            }, change: function (value, date, endDate) {
                                //时间被切换时回调
                            }, ready: function (date) {
                                //初始后回调
                                endDate[index].config.min.year = '1800';
                                endDate[index].config.min.month = '1';
                                endDate[index].config.min.date = '1';
                            }
                        });
                    });
                    //结束日期 class="endTime-input" name="endTimeInput"
                    $(".endTime-input").each(function (index) {
                        endDate[index] = laydate.render({
                            elem: this,
                            max: type === 0 ? new Date().format("yyyy-MM-dd") + " 23:59:59" : "2300-12-31 23:59:59",  //最大日期 当前日期
                            trigger: 'click', //采用click弹出
                            // //控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。
                            // value:当前选择的日期值
                            // date:得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                            done: function (value, date) {
                                if (value) {
                                    //当结束时间小于当前时间 则开始时间不能选择现在时间
                                    if (value < new Date().format("yyyy-MM-dd")) {
                                        //buttons.splice(1, 1);//移除 '现在' 按钮
                                        buttons.splice($.inArray('now', buttons), 1);
                                    } else {
                                        if ($.inArray("now", buttons) === -1) {
                                            buttons.push("now");
                                        }
                                    }
                                    startDate[index].config.max.year = date.year;
                                    startDate[index].config.max.month = date.month - 1;
                                    startDate[index].config.max.date = date.date;
                                } else {
                                    //有最大值限制(不能大于当前日期)
                                    if (type === 0) {
                                        startDate[index].config.max.year = new Date().getFullYear();
                                        startDate[index].config.max.month = new Date().getMonth();
                                        startDate[index].config.max.date = new Date().getDate();
                                        startDate[index].config.max.hours = '23';
                                        startDate[index].config.max.minutes = '59';
                                        startDate[index].config.max.seconds = '59';
                                    } else {
                                        startDate[index].config.max.year = '2300';
                                        startDate[index].config.max.month = '12';
                                        startDate[index].config.max.date = '12';
                                        startDate[index].config.max.hours = '23';
                                        startDate[index].config.max.minutes = '59';
                                        startDate[index].config.max.seconds = '59';
                                    }
                                }
                                endDate[index].config.value = value;
                                // 开始时间 结束时间均有值时触发搜索
                                // if (startDate[index].config.value && value) {
                                //     //触发表格刷新
                                //     setChangeEvent($("input[name='endTimeInput']"));
                                // }
                                // 结束时间有值时触发搜索
                                if (value) {
                                    //触发表格刷新
                                    var object = $(this)[0].elem;
                                    setChangeEvent(object);
                                    //手动绑定change事件 解决添加readonly属性后 无法触发已绑定的click、change事件  $(this)[0].elem.trigger("change");
                                    $(object).change();
                                }
                            }, change: function (value, date, endDate) {
                            }
                            , ready: function (date) {
                                if (type === 0) {
                                    startDate[index].config.max.year = new Date().getFullYear();
                                    startDate[index].config.max.month = new Date().getMonth();
                                    startDate[index].config.max.date = new Date().getDate();
                                    startDate[index].config.max.hours = '23';
                                    startDate[index].config.max.minutes = '59';
                                    startDate[index].config.max.seconds = '59';
                                } else {
                                    startDate[index].config.max.year = '2300';
                                    startDate[index].config.max.month = '12';
                                    startDate[index].config.max.date = '12';
                                    startDate[index].config.max.hours = '23';
                                    startDate[index].config.max.minutes = '59';
                                    startDate[index].config.max.seconds = '59';
                                }
                            }
                        });
                    });
                }
            },

            //单个日期选择插件 默认 yyyy-MM-dd type(为空:有最大日期,type=1:没有最大日期)
            //<input type="text" class="form-control time-input2" id="startTimeInput" placeholder="开始时间" name="startTimeInput" readonly style="width: 150px;background-color: #ffffff"/>
            dateInit2: function (type) {
                lay('.time-input2').each(function () {
                    laydate.render({
                        elem: this,
                        max: $.common.isEmpty(type) ? new Date().format("yyyy-MM-dd") : "2300-12-31 23:59:59",  //最大日期 当前日期
                        trigger: 'click'
                    });
                });
            },
            //年月选择器,单个日期选择插件 默认 yyyy-MM type(为空:有最大日期,type=1:没有最大日期)
            dateInit3: function (type) {
                lay('.time-input3').each(function () {
                    laydate.render({
                        elem: this,
                        type: 'month',
                        max: $.common.isEmpty(type) ? new Date().format("yyyy-MM") : "2300-12-31 23:59:59",
                        trigger: 'click'
                    });
                });
            },
            /**
             * 年月日 日期范围选择
             */
            dateRange: function () {
                lay('.dateRange').each(function () {
                    laydate.render({
                        elem: this,
                        btns: ['clear', 'now', 'confirm'],
                        range: true, //或 range: '-' 来自定义分割字符
                        trigger: 'click',
                        done: function (value, date, endDate) {
                            console.log(value); //得到日期生成的值，如：2017-08-18
                            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                            console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                        }
                    });
                });
            },
        }
    });
})(jQuery);

/** 正则方法封装处理**************************************************************************************************/
//金额格式(不能为0) 小数点后两位 0.01 0.1 0.10 1.0 1.01 1
var regMoney = /^((0(\.[0][1-9]))|(0(\.[1-9][0-9]?))|(([1-9][0-9]*)+(\.[0-9]{1,2})?))$/;
//金额格式(可以为0) 小数点后两位 0 0.01 0.1 0.10 1.0 1.01 1
var regMoney2 = /^(0|(0(\.[0][1-9]))|(0(\.[1-9][0-9]?))|(([1-9][0-9]*)+(\.[0-9]{1,2})?))$/;
//金额格式 小数点后两位 0.01 0.1 0.10 1.0 1.01 1（正负数）
var regMoney3 = /(^([-]?)[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^([-]?)(0){1}$)|(^([-]?)[0-9]\.[0-9]([0-9])?$)/;
//非零开头的最多带两位小数的数字 1 1.0 1.01
var renNumber = /^([1-9][0-9?]*)+(\.[0-9]{1,2})?$/;
//汉字匹配
var regChinese = /^[\u4e00-\u9fa5]{0,}$/;
//文件名称格式  数字 字母 _ . -  不能有空格
var regFileName = /^[-\u4E00-\u9FA5A-Za-z0-9_.]+$/;
//邮箱匹配
var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//6-12位字母数字
var regStr = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/;
//电话号码
var regPhone = /^1[0-9]{10}$/;
//身份证号(15位、18位数字)，最后一位是校验位，可能为数字或字符X
var regIDCard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
//非零正整数
var regNumber = /^[1-9]\d*$/;
//0到1，小数点后两位的小数
var regDecimal = /^(1|0(\.\d{1,2})?)$/;
//零和非零开头的数字
var regNumber2 = /^(0|[1-9][0-9]*)$/;
//有1-3位小数的正实数(可以以0开头)
var regNumber3 = /^[0-9]+(\.[0-9]{1,3})?$/;
//1到2位非零正整数
var regNumber4 = /^([1-9]|[1-9][0-9])$/;
// ipv4地址，例如255.255.255.255
var regIpv4=/^((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})(\.((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})){3}$/;

//富文本 打开编辑 div标签 #id
function openSummerNote(id, height) {
    // 默认高度200
    if ($.common.isEmpty(height) || height <= 0) {
        height = 200;
    }
    $(id).summernote({
        // focus: true,
        height: height,
        lang: 'zh-CN',
        //图片上传
        callbacks: {
            onImageUpload: function (files) {
                uploadSummerNoteImage(files, id);
            }
        }
    });
}

//校验 富文本内容是否为空 element为id: #id  为class: .class
function checkSummernoteCodeIsEmpty(element) {
    //true空 false不为空
    return $(element).summernote('isEmpty');
}

//富文本 保存
// summerNoteId:富文本id
// id:保存时数据表的id  url:保存时请求的路径
function saveSummerNote(summerNoteId, id, url) {
    //获取编辑器内的HTML内容
    var markup = $(summerNoteId).summernote('code');
    //销毁富文本编辑器
    $(summerNoteId).summernote('destroy');
    //这里对富文本内容进行转码后 encodeURI(newMarkup)
    // 在后台用 URLDecoder.decode(params.get("content").toString(), "utf-8") 解码再存
    $.operate.post(url, {"id": id, "content": encodeURI(markup)}, function (r) {
        $.modal.msgSuccess("操作成功");
        $.modal.lock = false;
    });
}

//富文本 图片上传回调地址 支持多文件上传
//files:富文本选择文件后的对象 summerNoteId:富文本id 包含 #
function uploadSummerNoteImage(files, summerNoteId) {
    //上传图片到服务器
    var formData = new FormData();
    //同时选择多张图片
    for (var i = 0; i < files.length; i++) {
        formData.append('file', files[i])
    }
    $.operate.postFile(formData, function (res) {
        //重新给img src赋值 并在编辑器显示
        var data = res.data;
        for (var i = 0; i < data.length; i++) {
            $(summerNoteId).summernote('insertImage', data[i].url, function ($image) {
                $image.attr('style', 'max-width:100%;');
                $image.attr('data-filename', 'img');
            });
        }
    });
}

//富文本赋值 element 为 id 或 class
function setSummerNoteContent(element, content) {
    $(element).summernote('code', content);
}

/** js法封装处理*********************************************************************************************************/
//form序列化为json
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//字母数组
var letter = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

// 文件查看/下载
function lookOrDownloadFile(url) {
    var nameStr = url.split("/");
    var name = nameStr[nameStr.length - 1];
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);//get请求，请求地址，是否异步
    xhr.responseType = "blob";    // 返回类型blob
    xhr.onload = function () {// 请求完成处理函数
        if (this.status === 200) {
            var blob = this.response;// 获取返回值
            var a = document.createElement('a');
            a.download = name;
            a.href = window.URL.createObjectURL(blob);
            a.click();
        }
    };
    // 发送ajax请求
    xhr.send();
}

/**
 * 获取页面传过来的值
 * @param name
 * @returns {string}
 * @constructor
 */
function getValueByUrl(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    return r ? decodeURIComponent(r[2]) : "";
}


$(document).ready(function () {
    //单选按钮样式 <div class="radio i-checks radio-inline">
    if ($('.i-checks').length > 0) {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green'
        });
    }

    //打开富文本编辑框 <div class="summernote" id="productDetails"></div>
    $(".summernote").each(function () {
        openSummerNote('#' + $(this).attr("id"), "");
    });

});

//日期格式转换方法  2015-02-21 fmt:日期格式 yyyy-MM-dd hh:mm:ss
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

//图片可访问路径
function imageCompleteUrl(url) {
    return (baseURL + url).replace("//", "/");
}

//点击图片 单个 显示大图
// value可以为图片路径 不是数据库表存的路径 应该是加了../.. 的路径 type = 1
// value可以为 含有图片路径的标签对象 如：img标签(含有src属性) input标签(含url属性) 方法:showBigImage(this) 传type = 2
function showBigImage(value, type) {
    var url;
    if (type == 1) {
        url = value;
    } else {
        url = $(value).attr("src") ? $(value).attr("src") : $(value).attr("url");
    }
    if (url) {
        layer.photos({
            closeBtn: 1,    //右上角的关闭 0无
            shade: 0,       //透明度及背景色[0.8, ‘#393D49’]；不想显示遮罩可以shade: 0
            shadeClose: true, //点击弹层外区域关闭
            photos: {
                "title": "", //相册标题
                "id": 1, //相册id
                "start": 0, //初始显示的图片序号，默认0
                "data": [   //相册包含的图片，数组格式
                    {
                        "alt": "",
                        "pid": 1, //图片id
                        "src": url, //原图地址
                        "thumb": url //缩略图地址
                    }
                ]
            }
            , anim: 5 //0-6的选择，指定弹出图片动画类型 0平滑放大 1从上掉落 2从最底部往上滑入 3从左滑入 4从左翻滚 5渐显 6抖动
        });
    }
}

//页面层-图片
// type = 1 value为图片全路径 浏览器可以直接访问到
// type = 2 value为 含有图片路径的标签对象 如：img标签(含有src属性) input标签(含url属性) showBigImage2(this, 2)
function showBigImage2(value, type) {
    var url;
    if (type === 1) {
        url = value;
    } else {
        var src = $(value).attr("src");
        if (src) {
            url = src;
        } else {
            url = $(value).attr("url");
        }
    }
    layer.open({
        //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        type: 1,
        title: false,
        //右上角关闭按钮 1和2来展示 0不显示
        closeBtn: 1,
        area: [($(window).width() - 100) + "px", ($(window).height() - 30) + "px"],//'auto'
        skin: 'layui-layer-nobg', //没有背景色
        //是否点击遮罩关闭
        shadeClose: true,
        move: 'img',  //触发拖动的元素
        moveOut: false,//是否允许拖拽到窗口外
        content: "<img src=\"" + url + "\" style='max-width: 100%;width:100%;object-fit: cover;max-height: inherit'>"
    });
}


//查看图片 title：弹窗标题 url：图片路径 已拼接../.. 多个用逗号隔开
function imageInfo(title, url) {
    var img = url.split(",");
    if (img) {
        var html = "<div class=\"row imageInfo\" style='margin-left: 10px;margin-right: 10px'></div>";
        $.modal.open(title, html, "", "取消", false, 800, 600, function (index, layero) {
        });
        for (var i = 0; i < img.length; i++) {
            $("        <div class=\"col-sm-6\" style='margin-left: 20px;margin-top: 20px'>\n" +
                "       <img alt=\"加载中\" src=\"" + img[i] + "\" style=\"width:100%;max-width:100%;max-height:200px;cursor:pointer;object-fit:cover\" onclick=\"showBigImage(this,2)\">\n" +
                "                                        </div>\n").appendTo(".row.imageInfo");
        }
    }
}

/**
 * 动态创建选项卡
 * dataUrl:页面跳转路径  sys/user/add.html
 * menuName:菜单名称
 * */
function createMenuItem(dataUrl, menuName) {
    var panelUrl = window.frameElement.getAttribute('data-id');
    var dataIndex = $.common.random(1, 100);
    var flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) {
        return false;
    }
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.J_menuTab', topWindow).each(function () {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                $('.page-tabs-content').animate({marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.J_mainContent .J_iframe', topWindow).each(function () {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.J_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '" data-panel="' + panelUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.J_menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" data-panel="' + panelUrl + '" seamless></iframe>';
        $('.J_mainContent', topWindow).find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);

        // 添加选项卡
        $('.J_menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}

//图片预览
function imagePreview(fileId, PreviewId) {
    //图片
    if (document.getElementById(fileId) != null) {
        document.getElementById(fileId).onchange = function () {
            var file = document.getElementById(fileId).files[0];
            var r = new FileReader();  //本地预览
            r.onload = function () {
                $('#' + PreviewId).attr('src', r.result);
            };
            r.readAsDataURL(file);    //Base64
        };
    }
}


/**
 * 当前本页面自定义确定按钮后 校验必填选项 及 特殊字段格式
 * class="confirm"
 */
$(".confirm").on("click", function () {
    //错误次数
    var errorCount = 0;
    $(".required").each(function () {
        if ($.common.isEmpty($(this).val())) {
            $(this).addClass("input-error");
            errorCount = errorCount + 1;
        } else {
            $(this).removeClass("input-error");
        }
    });
    if (errorCount != 0) {
        $.modal.msgWarning("请先完善信息后再提交");
        return false;
    }
    //简单校验 电话格式 class="phone"
    $(".phone").each(function () {
        var phone = $(this).val();
        if (phone && !regPhone.test(phone)) {
            $(this).addClass("input-error");
            $.modal.msgWarning($(this).attr("name") + "格式错误");
            errorCount++;
            return false;
        } else {
            $(this).removeClass("input-error");
        }
    });
    if (errorCount != 0) {
        return false;
    }
    //简单校验 金额格式 class="money" 可以为0
    $(".money").each(function () {
        var money = $(this).val();
        if (money && !regMoney2.test(money)) {
            $(this).addClass("input-error");
            $.modal.msgWarning($(this).attr("name") + "格式错误");
            errorCount++;
            return false;
        } else {
            $(this).removeClass("input-error");
        }
    });
    if (errorCount != 0) {
        return false;
    }

    //简单校验 数字格式 class="number"
    $(".number").each(function () {
        var number = $(this).val();
        if (number && !regNumber2.test(number)) {
            $(this).addClass("input-error");
            $.modal.msgWarning($(this).attr("name") + "格式错误");
            errorCount++;
            return false;
        } else {
            $(this).removeClass("input-error");
        }
    });
    if (errorCount != 0) {
        return false;
    }
    //回调当前页面的 确认方法
    submitHandler();
});
/**
 * 页面自定义上传文件按钮 class="uploadFileButton" inputFileId="goodsUrl"  点击后触发 <input id="goodsUrl" type="file">点击 从而选择文件
 * 仅用于上传单文件
 */
$(".uploadFileButton").on("click", function () {
    var inputFileId = $(this).attr("inputFileId");
    if (inputFileId) {
        $('#' + inputFileId).trigger("click");
    }
});

/**
 * 点击图片 查看大图
 * class="showBigImage"
 */
$(".showBigImage").on("click", function () {
    //showBigImage2(this, 2);
    showBigImage(this, 2);
    console.log("触发了")
});

/**
 *
 * 上传图片 单/多文件上传  多文件上传添加此属性 multiple ismore=true多张(每次选择文件后不自动清空已上传文件) false单张(每次选择文件后自动清空已上传文件)
 * <input type="file" accept=".png,.jpg,.gif,.jpeg,.gif,.jfif"  class="uploadMultipleImgInput" ismore="true"> 点击后触发
 *
 * 多文件上传 图片显示写法
 *   <div class="row">
 *      <div class="bannerImg" style="display: flex"></div>
 *   </div>
 */
$(".uploadImgInput").on("change", function () {
    //文件输入框的值
    var fileValue = $(this).val();
    //是否上传多张图片 true多张(每次选择文件后不自动清空已上传文件) false单张(每次选择文件后自动清空已上传文件)
    var ismore = $(this).attr("ismore");
    //如果文件输入框有值 上传图片
    if (fileValue) {
        var fileId = $(this).attr("id");
        if (!fileValue.match(/.jpg|.gif|.png|.jpeg|.bmp|.jfif|.tif/i)) {　　//判断上传文件格式
            $.modal.msgWarning("上传的图片格式不支持，请重新选择");
            return false;
        }
        var formData = new FormData();
        //同时选择多张图片
        for (var i = 0; i < $(this)[0].files.length; i++) {
            formData.append('file', $(this)[0].files[i]);
        }
        var url = "";
        $.operate.postFile(formData, function (res) {
            var data = res.data;
            $.modal.msgSuccessDelay("文件上传成功", function () {
                //单文件上传 每次选择文件后自动清空已上传文件
                if (!ismore) {
                    $("." + fileId).empty();
                }
                for (var i = 0; i < data.length; i++) {
                    if (ismore) {
                        //默认将图片url用逗号隔开
                        url = url + "," + data[i].url;
                    } else {
                        url = data[i].url;
                    }
                    //显示上传的图片
                    imgShow(ismore, data[i].url, fileId,i);
                }
                //默认将所有图片url存在input标签里
                if (ismore) {
                    //多文件上传 url逗号拼接
                    if ($("#" + fileId).attr("url")) {
                        $("#" + fileId).attr("url", $("#" + fileId).attr("url") + url);
                    } else {
                        $("#" + fileId).attr("url", url.substring(1));
                    }
                } else {
                    //单文件上传
                    $("#" + fileId).attr("url", url);
                }
            });
        });
        $.modal.lock = false;
    }
});

/**
 * 上传视频  多文件上传添加此属性 multiple
 * <input type="file"  class="uploadVideoInput"> 点击后触发
 */
$(".uploadVideoInput").on("change", function () {
    //文件输入框的值
    var fileValue = $(this).val();
    //如果文件输入框有值 上传图片
    if (fileValue) {
        var id = $(this).attr("id");
        // 文件格式
        if (!fileValue.match(/.avi|.rmvb|.tar|.mp4|.bmp|.mkv/i)) {　　//判断上传文件格式
            $.modal.msgWarning("上传的视频格式不支持，请重新选择");
            return false;
        }
        var formData = new FormData();
        //同时选择多个视频
        for (var i = 0; i < $(this)[0].files.length; i++) {
            formData.append('file', $(this)[0].files[i]);
        }
        var url = "";
        $.operate.postFile(formData, function (res) {
            var data = res.data;
            console.log(data);
            $.modal.msgSuccessDelay("文件上传成功", function () {
                console.log($("." + id));
                $("." + id).empty();
                for (var i = 0; i < data.length; i++) {
                    url = url + "," + data[i].url;
                    $("<video width='300' height='500' src='" + data[i].url + "' controls></video>").appendTo("." + id);
                }
                $("#" + id).attr("url", url.substring(1));
            });
        });
        $.modal.lock = false;
    }
});

/**
 * 图片文件显示
 * @param ismore=true多文件(每次选择文件后不自动清空已上传文件) false单文件(每次选择文件后自动清空已上传文件)
 */
function imgShow(ismore, imgUrl, fileId,key) {
    if (ismore) {
        //唯一标识
        var item = Date.now()+key;
        var del="<span class='btn_del_pictures' fileid='"+fileId+"' id='"+item+"' " +
            "style='position: absolute;z-index: 2;top: 0px;right: 13px;width: 15px;height: 15px;line-height: 15px;text-align: center;cursor: pointer;background-color: #F0F0F0'>x</span>";
        $("<div class='col-sm-4 col-md-4' id='" + item + "'></div>")
            .append('<img class="showBigImage" id="' + item + '" src="' + imgUrl + '" alt="正在加载中..." style="background-color: #EEEEEE;width: 100%;max-width:100px;height:100%;max-height:100px;object-fit:cover;border-color: #bdbdbd;"/>')
            .append(del)
            .appendTo("." + fileId);
    } else {
        $("   <img class='showBigImage' src=\"" + imgUrl + "\" style=\"background-color: #EEEEEE;object-fit:cover;border-color: #bdbdbd;\"\n" +
            "                   />").appendTo("." + fileId);
    }
    //绑定 点击图片放大事件
    $(".showBigImage").on("click", function () {
        showBigImage(this, 2);
    });
    //多文件上传 绑定 删除事件
    $(".btn_del_pictures").on("click", function () {
        //获取唯一标识
        var item = $(this).attr("id");
        //根据唯一标识 获取当前被删除图片的url
        var src = $("img[id=" + item + "]").attr("src");
        //获取用于存放url的input id
        var fileId = $(this).attr("fileid");
        //获取input的url值
        var url = $("#" + fileId).attr("url");
        var indexOf = url.indexOf(src);
        if (indexOf != -1) {
            if (indexOf == 0) {
                url = url.replace(src, "").substring(1);
            } else if (indexOf > 0) {
                url = url.replace("," + src, "");
            }
            $("#" + fileId).attr("url", url);
        }
        //移除图片
        $("#" + item).remove();
    });
}
