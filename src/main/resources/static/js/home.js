$(function () {
    reloadMenu();
    $("#documentName").on("keypress", function (e) {
        if (e.keyCode === 13) {
            searchDocument();
        }
    });
    $("#logout").on("click", function () {
        layer.confirm("确定要退出系统吗", {icon: 3, anim: 0}, function (yes) {
            $.post("/logout",{}, function (data) {
                if (data.code === 200) {
                    window.location.href="/index";
                } else {
                    $.error(data.msg);
                }
            })
        });
    });
});

/**
 * 加载右键菜单
 */
function reloadMenu() {
    $("#centerMenu").smartMenu(centerMenu, {name: "centerMenu", textLimit: 10});
    $(".documentMenu").smartMenu(fileMenu, {name: "fileMenu", textLimit: 10});
    $(".friendMenu").smartMenu(friendMenu, {name: "friendMenu"});
}


/**
 * 创建word文档
 */
function createDocument(docName, data) {
    data.docName = docName;
    data.spaceId = spaceId;
    $.post("/createDocument", data, function (data) {
        if (data.result === 200) {
            layer.alert("创建文档成功", {icon: 6});
            loadDocument(data.msg, false);
        } else {
            $.error(data.msg);
        }
    });
}


/**
 * 查询功能
 */
function searchDocument() {
    let docName = $("#documentName").val();
    let showHidden = window.localStorage.getItem("showHidden");
    $.ajax("/searchDocument", {
        "type": "get",
        "data": {
            "spaceId": spaceId,
            "docName": docName,
            "showHidden": showHidden,
            "orderColumn": getOrderKey(),
            "orderType": getOrderType()
        },
        "success": function (data) {
            if (data.code === 200) {
                loadDocument(data.data, true);
            } else {
                $.error(data.msg);
            }
        }
    });
}

/**
 * 获取排序规则
 * @return {string}
 */
function getOrderKey() {
    return window.localStorage.getItem("orderColumn");
}

/**
 * 获取下次排序规则
 * @returns {string|string}
 */
function getOrderType() {
    let orderType = window.localStorage.getItem("orderType");
    let nextOrderType = (orderType === "ASC") ? "DESC" : "ASC";
    window.localStorage.setItem("orderType", nextOrderType);
    return nextOrderType;
}


/**
 * 添加文档
 * @param entity
 * @param isList 是否为列表
 */
function loadDocument(entity, isList) {
    let html = "";
    if (entity != null && !$.isEmptyObject(entity)) {
        if (isList) {
            $.each(entity, function (i, v) {
                html += formatHtml(v);
            });
            $("#documentId").empty().append(html);
        } else if ($("#noDocument").length !== 0) {
            $("#documentId").empty().append(formatHtml(entity));
        } else {
            $("#documentId").append(formatHtml(entity));
        }
    } else {
        $("#documentId").empty().append('<p id="noDocument" class="text-center text-muted center-vertical">暂无文档</p>');
    }
    reloadMenu();
}

function formatHtml(entity) {
    let html = '<div class="col-xs-2 underline">';
    html += '<a href="#" class="thumbnail documentMenu ' + (entity.hidden ? "hidden-backgroud" : "") + '" >';
    html += '<img alt="' + entity.type + '" src="./images/' + entity.type + '.png" style="height: 100px;  display: block;" >';
    html += '<span>' + entity.docName + '</span> <input type="hidden" value="' + entity.id + '" class="id" /></a> <input type="hidden" value="' + entity.type + '" class="type" /></a></div>';
    return html;
}


function updateDocument(newValue, json) {
    json["docName"] = newValue;
    $.post("/updateDocument", json, function (data) {
        if (data.result === 200) {
            $.right(data.msg);
            searchDocument();
        } else {
            $.error(data.msg);
        }
    });
}


/**
 * 加密
 * @param value 加密数据
 * @param json json串
 */
function addPassword(value, json) {
    json["pwd"] = value;
    $.post("/createPassword", json, function (data) {
        if (data.result === 200) {
            $.right(data.msg);
        } else {
            $.error(data.msg);
        }
    });
}


let centerMenu = [[{
    text: "排序",
    data: [[{
        text: "名称",
        func: function () {
            window.localStorage.setItem("orderColumn", "docName");
            searchDocument();
        }
    }, {
        text: "修改时间",
        func: function () {
            window.localStorage.setItem("orderColumn", "updateTime");
            searchDocument();
        }
    }, {
        text: "类型",
        func: function () {
            window.localStorage.setItem("orderColumn", "type");
            searchDocument();
        }
    }]]
}, {
    text: "刷新",
    func: function () {
        searchDocument();
    }
}, {
    text: "新建",
    data: [[
        {
            text: "Word文档",
            func: function () {
                $.showPrompt('请输入Word文档名称', '', {'fileType': 'WORD'}, createDocument);
            }
        }, {
            text: "PPT文稿",
            func: function () {
                $.showPrompt('请输入PPT文档名称', '', {'fileType': 'PPT'}, createDocument);
            }
        }, {
            text: "Markdown文档",
            func: function () {
                $.showPrompt('请输入Markdown文档名称', '', {'fileType': 'MD'}, createDocument);
            }
        }
    ]]
}, {
    text: "显示/隐藏",
    func: function () {
        let showHidden = window.localStorage.getItem("showHidden");
        let nextAction = showHidden === 'false' ? 'true' : 'false';
        window.localStorage.setItem("showHidden", nextAction);
        searchDocument();
    }
}]];




let fileMenu = [[{
    text: "打开",
    func: function () {
        let hasPwd = $(this).children(".hasPwd").val();
        let id = $(this).children(".id").val();
        let author = $(this).children(".author").val();
        if (hasPwd && author !== userId) {
            $.showPrompt("请输入文档密码",'', {'docId': id}, function (value, json) {
                let timestamp = new Date().getTime();
                let pwd = md5(md5(value) + timestamp);
                json["pwd"] = pwd;
                json["timestamp"] = timestamp;
                $.post("/checkPassword", json, function (data) {
                    if (data.code === 200) {
                        window.open("/document/" + spaceId + "/" + id + "?p=" + pwd + "&t=" + timestamp);
                    } else {
                        $.error("密码输入错误");
                    }
                });
            })
        } else {
            window.open("/document/" + spaceId + "/" + id);
        }
    }
}, {
    text: "排序",
    data: [[{
        text: "名称",
        func: function () {
            let $orderBy = $("#orderBy");
            let orderBy = $orderBy.val();
            if (orderBy === "docName,desc") {
                $orderBy.val("docName,asc");
            } else if (orderBy === "docName,asc") {
                $orderBy.val("docName,desc");
            }
            searchDocument();
        }
    }, {
        text: "修改时间",
        func: function () {
            let $orderBy = $("#orderBy");
            let orderBy = $orderBy.val();
            if (orderBy === "updateDate,desc") {
                $orderBy.val("updateDate,asc");
            } else if (orderBy === "updateDate,asc") {
                $orderBy.val("updateDate,desc");
            }
            searchDocument();
        }
    }, {
        text: "类型",
        func: function () {
            let $orderBy = $("#orderBy");
            let orderBy = $orderBy.val();
            if (orderBy === "type,desc") {
                $orderBy.val("type,asc");
            } else if (orderBy === "type,asc") {
                $orderBy.val("type,desc");
            }
            searchDocument();
        }
    }]]
}, {
    text: "刷新",
    func: function () {
        searchDocument();
    }
}], [{
    text: "删除",
    func: function () {
        let id = $(this).children(".id").val();
        $.deleteFun("/deleteDocument/" + spaceId, id, "确定要删除该文档吗?", null, searchDocument);
    }
}, {
    text: "重命名",
    func: function () {
        let docName = $(this).children("span").text();
        let id = $(this).children(".id").val();
        $.showPrompt("请输入文档的新名称", docName, {"id": id}, updateDocument);

    }
}, {
    text: "新建",
    data: [[
        {
            text: "Word文档",
            func: function () {
                $.showPrompt('请输入Word文档名称', '', {'fileType': 'WORD'}, createDocument);
            }
        },
        {
            text: "Markdown文稿",
            func: function () {
                $.showPrompt('请输入Markdown文档名称', '', {'fileType': 'MD'}, createDocument);
            }
        },
        {
            text: "PPT文稿",
            func: function () {
                $.showPrompt('请输入PPT文档名称', '', {'fileType': 'PPT'}, createDocument);
            }
        }
    ]]
}, {
    text: "加密",
    func: function () {
        let docName = $(this).children("span").text();
        let id = $(this).children(".id").val();
        $.password(docName + " 加密", 6, {"docId": id}, addPassword);
    }
}, {
    text: "属性",
    func: function () {
        let docName = $(this).children("span").text();

        let html = "<form class='form-horizontal'>";
        html += "<div class='from-group'>";
        html += "<label  class='col-xs-4 control-label'><small>创建人</small></label><div class='col-xs-8'><label  class='control-label'><small>张珊<small></label></div>";
        html += "</div>";
        html += "<div class='from-group'>";
        html += "<label  class='col-xs-4 control-label'><small>创建时间</small></label><div class='col-xs-8 '><label  class='control-label'><small>2015-21-21 33:33:33<small></label></div>";
        html += "</div>";
        html += "<div class='from-group'>";
        html += "<label  class='col-xs-4 control-label'><small>修改时间</small></label><div class='col-xs-8 '><label  class='control-label'><small>2015-21-21 33:33:33<small></label></div>";
        html += "</div>";
        html += "<div class='from-group'>";
        html += "<label  class='col-xs-4 control-label'><small>属性</small></label><div class='col-xs-8'><div class='checkbox'><label><input type='checkbox'/><p style='font-weight: bold'>隐藏</p></label></div></div>";
        html += "</div>";
        html += "</form>";
        $.window(html, docName + " 的属性");
    }
}]];
let friendMenu = [[{
    text: "发送信息",
    func: function () {
        // TODO 待完成
    }
}, {
    text: "修改备注",
    func: function () {

    }
}], [{
    text: "删除",
    func: function () {

    }
}, {
    text: "查看资料",
    func: function () {

    }
}]];