$(function () {
    reloadMenu();
    layer.config({
        extend: ['extend/layer.ext.js']
    });
    $("#documentName").on("keypress", function (e) {
        if (e.keyCode == 13) {
            searchDocument();
        }
    });

});

/**
 * 加载右键菜单
 */
function reloadMenu() {
    $("#centerMenu").smartMenu(centerMenu, {name: "centerMenu"});
    $(".documentMenu").smartMenu(fileMenu, {name: "fileMenu"});
    $(".friendMenu").smartMenu(friendMenu, {name: "friendMenu"});
}


/**
 * 创建word文档
 */
function createDocument(docName, type) {
    $.post("/createDocument/" + workspaceId, {"docName": docName, "type": type}, function (data) {
        if (data.result) {
            layer.alert("创建文档成功", {icon: 1});
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
    let isHidden = $("#showHideDocument").val();
    let order = $("#orderBy").val();
    $.post("/getDocument/" + workspaceId, {"docName": docName, "hidden": isHidden, "order": order}, function (data) {
        if (data.result) {
            loadDocument(data.msg, true);
        } else {
            $.error(data.msg);
        }
    });
}


/**
 * 添加文档
 * @param entity
 * @param list是否为列表
 */
function loadDocument(entity, list) {
    var html = "";
    if (entity != null && !$.isEmptyObject(entity)) {
        if (list) {
            $.each(entity, function (i, v) {
                html += formatHtml(v);
            });
            $("#documentId").empty().append(html);
        } else if ($("#noDocument").length != 0) {
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
    var html = '<div class="col-xs-2 underline">';
    html += '<a href="#" class="thumbnail documentMenu ' + (entity.isShow == "1" ? "hidden-backgroud" : "") + '" >';
    html += '<img alt="' + entity.type + '" src="./images/icon_' + entity.type + '.png" style="height: 100px;  display: block;" >';
    html += '<span>' + entity.docName + '</span> <input type="hidden" value="' + entity.id + '" class="id" /></a> <input type="hidden" value="' + entity.type + '" class="type" /></a></div>';

    return html;
}


function updateDocument(newValue, id) {
    $.post("/updateDocument/" + workspaceId, {"docName": newValue, "id": id}, function (data) {
        if (data.result) {
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
 * @param message 附加信息
 */
function addPassword(value, message) {
    let sendData = $.extend({}, message);
    sendData["docPassword"] = value;
    $.post("/createPassword/" + workspaceId, sendData, function (data) {
        if (data.result) {
            $.right(data.msg);
        } else {
            $.error(data.msg);
        }
    });
}


var centerMenu = [[{
    text: "排序",
    data: [[{
        text: "名称",
        func: function () {
            var orderBy = $("#orderBy").val();
            if (orderBy == "docName,asc") {
                $("#orderBy").val("docName,desc");
            } else {
                $("#orderBy").val("docName,asc");
            }
            searchDocument();
        }
    }, {
        text: "修改时间",
        func: function () {
            var orderBy = $("#orderBy").val();
            if (orderBy == "updateDate,asc") {
                $("#orderBy").val("updateDate,desc");
            } else {
                $("#orderBy").val("updateDate,asc");
            }
            searchDocument();
        }
    }, {
        text: "类型",
        func: function () {
            var orderBy = $("#orderBy").val();
            if (orderBy == "type,asc") {
                $("#orderBy").val("type,desc");
            } else {
                $("#orderBy").val("type,asc");
            }
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
                $.showPrompt('请输入Word文档名称', createDocument, 'word');
            }
        }, {
            text: "PPT文稿",
            func: function () {
                $.showPrompt('请输入PPT文档名称', createDocument, 'ppt');
            }
        }
    ]]
}, {
    text: "显示隐藏",
    func: function () {
        var isShow = $("#showHideDocument").val();
        if (isShow == "0" || isShow == null || isShow == "") {
            $("#showHideDocument").val("1");
        } else if (isShow == "1") {
            $("#showHideDocument").val("0");
        }
        searchDocument();
    }
}]];


function openNewWindow(url) {
    document.document.action = url;
    window.open('about:blank', 'newWindow');
    document.document.target = 'newWindow';
    document.document.submit();
}


var fileMenu = [[{
    text: "打开",
    func: function () {
        var id = $(this).children(".id").val();
        openNewWindow("/document/" + workspaceId + "/" + id);
    }
}, {
    text: "排序",
    data: [[{
        text: "名称",
        func: function () {
            var orderBy = $("#orderBy").val();
            if (orderBy == "docName,desc" || orderBy == null || orderBy == "") {
                $("#orderBy").val("docName,asc");
            } else if (orderBy == "docName,asc") {
                $("#orderBy").val("docName,desc");
            }
            searchDocument();
        }
    }, {
        text: "修改时间",
        func: function () {
            var orderBy = $("#orderBy").val();
            if (orderBy == "updateDate,desc" || orderBy == null || orderBy == "") {
                $("#orderBy").val("updateDate,asc");
            } else if (orderBy == "updateDate,asc") {
                $("#orderBy").val("updateDate,desc");
            }
            searchDocument();
        }
    }, {
        text: "类型",
        func: function () {
            var orderBy = $("#orderBy").val();
            if (orderBy == "type,desc" || orderBy == null || orderBy == "") {
                $("#orderBy").val("type,asc");
            } else if (orderBy == "type,asc") {
                $("#orderBy").val("type,desc");
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
        var id = $(this).children(".id").val();
        $.deleteFun("/deleteDocument/" + workspaceId, id, "确定要删除该文档吗?", null, searchDocument);
    }
}, {
    text: "重命名",
    func: function () {
        var docName = $(this).children("span").text();
        var id = $(this).children(".id").val();
        $.showPrompt("请输入文档的新名称", updateDocument, id, docName);

    }
}, {
    text: "新建",
    data: [[
        {
            text: "Word文档",
            func: function () {
                $.showPrompt('请输入Word文档名称', createDocument, 'word');
            }
        }, {
            text: "PPT文稿",
            func: function () {
                $.showPrompt('请输入PPT文档名称', createDocument, 'ppt');
            }
        }
    ]]
}, {
    text: "加密",
    func: function () {
        var docName = $(this).children("span").text();
        var id = $(this).children(".id").val();
        $.password(docName + " 加密", 1, addPassword, 6, {"id": id});
    }
}, {
    text: "属性",
    func: function () {
        var docName = $(this).children("span").text();

        var html = "<form class='form-horizontal'>";
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
var friendMenu = [[{
    text: "发送信息",
    func: function () {

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