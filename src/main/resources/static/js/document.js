let load = null;
let ueditor = null;
let stompClient;
let $chatMessage = $("#chatMessage");
let $pageId = $("#pageId");
$(function () {
    ueditor = UE.getEditor("container");
    load = $.loadMsg("服务器连接中...");
    connectServer();
    $chatMessage.on("keypress", function (event) {
        if (event.keyCode === 13) {
            sendMessage();
        }
    });
});


/**
 * websocket首次建立连接
 * @param endpoint 链接地址
 * @param num 重试次数
 */
let connectServer = function(endpoint, num) {
    if (num > 10) {
        console.error("websocket服务链接超时");
        return;
    }
    let socket = new SockJS(endpoint);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        if (load != null) {
            layer.close(load);
        }
        initDoc();
        initChat();
    }, function (err) {
        console.error("websocket链接失败:" + err);
        connectServer(endpoint, num++);
    });
};


/**
 * 初始化文档编辑
 */
let initDoc = function () {
    subscribe("/websocket/initDocument/" + workspaceId + "/" + documentId, function (json) {
        loadLeftFileList(json.document.documentFiles);
        let canWrite = (json.document.userId === userId);
        loadCenterFile(json.document.documentFiles[0], canWrite);
        forEachChat(json.chat);
    });
};

/**
 * 初始化聊天信息
 * 订阅编辑信息
 */
let initChat = function() {
    subscribe("/document/" + workspaceId + "/" + documentId, function (json) {
        let type = json.type;
        switch (type) {
            case 1: // 聊天信息内容
                showChat(json.data);
                break;
            case 2: // 编辑器内容同步
                updateContent(json.data);
                break;
        }
    });
};

/**
 * 订阅
 * @param subscribeUrl 订阅地址
 * @param callback 回调地址,返回值中包含解析过的json对象
 */
let subscribe = function(subscribeUrl, callback) {
    if (stompClient) {
        stompClient.subscribe(subscribeUrl, function (data) {
            console.log("subscribeResp:" + data.body);
            if (typeof callback === "function" ) {
                let jsonResult = JSON.parse(data.body);
                callback(jsonResult);
            }
        });
    } else {
        console.error("subscribe失败,client未初始化");
    }
};


/**
 * 加载左侧列表
 * @param obj 要加载的列表 Array
 */
function loadLeftFileList(obj) {
    $.each(obj, function (i, v) {
        if (i === 0) {
            $("#leftFileList").append('<div class="col-xs-12"><a class="thumbnail selectActive" onclick="changePage(this,' + v.id + ');" href="javascript:void(0)"><img alt="" src="/images/ppt_fanye.png" style="height: 100px;  display: block;"></a></div>');
            $pageId.val(v.id);
        } else {
            $("#leftFileList").append('<div class="col-xs-12"><a class="thumbnail" onclick="changePage(this,' + v.id + ');" href="javascript:void(0)"><img alt="" src="/images/ppt_fanye.png" style="height: 100px;  display: block;"></a></div>');
        }
    });
}

/**
 * 添加事件监听
 */
function addEvent() {
    ueditor.addListener("contentChange", sendContentMsg);
}

function removeEvent() {
    ueditor.removeListener("contentChange", sendContentMsg);
}

function sendContentMsg() {
    updatePageContent($pageId.val(), ueditor.getContent());
}

/**
 * 切换文档页事件
 * @param id
 * @param obj
 */
function changePage(obj, id) {
    let fileId = $pageId.val();
    if (fileId !== id) {
        $pageId.val(id);
        $.post("/changePage", {"id": id}, function (data) {
            removeEvent();
            ueditor.setContent(data.content);
            $("#leftFileList a").removeClass("selectActive");
            $(obj).addClass("selectActive");
            addEvent();
        });
    }
}

/**
 * 初始编辑器内容
 * @param obj
 * @param canWrite 是否可以编辑文档
 */

function loadCenterFile(obj, canWrite) {
    ueditor.setContent(obj.content);
    if (canWrite) {
        ueditor.setEnabled();
        addEvent();
    } else {
        ueditor.setDisabled(["fullscreen", "preview"]);
    }

}


/**
 * 订阅文档编辑空间(中)
 * @param data 服务端响应信息
 */
function updateContent(data) {
    let fileId = $("#fileId").val();
    if (userId !== data.userId && fileId === data.id) {
        removeEvent();
        ueditor.setContent(data.content);
        addEvent();
    }
}


/**
 * 循环遍历输出聊天信息
 * @param data
 */
function forEachChat(data) {
    if (!$.isEmpty(data)) {
        $.each(data, function (i, v) {
            showChat(v);
        });
    }
}

/**
 * 发送和接收聊天信息
 */
function sendMsg() {
    let msg = $.trim($chatMessage.val());
    if (msg) {
        send("/app/userChat", {
            'chatContent': encodeURIComponent(msg),
            'documentId': documentId,
            'workspaceId': workspaceId
        });
    }
    $chatMessage.focus();
}

/**
 * 发送文档内容
 * @param id
 * @param msg 文档内容
 */
function updatePageContent(id, msg) {
    send("/app/updatePage",{
        'content': encodeURIComponent(msg),
        'id': id,
        "documentId": documentId,
        "workspaceId": workspaceId
    })
}

let send = function (sendUrl, json) {
    if (stompClient) {
        stompClient.send(sendUrl, {}, JSON.stringify(json));
    } else {
        console.error("send失败,client未初始化");
    }
};

/**
 * 显示聊天信息
 * @param data 后台返回的信息json
 */
function showChat(data) {
    let active = "p_noactive";
    if (data.id === userId) {
        active = "p_active";
        $chatMessage.val("");
    }
    $("#chatRoom").append(contentHtml(data.nickName, data.createTime, data.chatContent, active));
    let div = document.getElementById("chatRoom");
    div.scrollTop = div.scrollHeight;
}


/**
 * 组装聊天信息
 * @param name 发言人
 * @param msg 发言内容
 * @param active 状态
 * @returns {string}
 * @param time 发送时间
 */
function contentHtml(name, time, msg, active) {
    return "<p class='" + active + "'>" + name + " " + time + "</p>" + "<p class='chatContent'>" + msg + "</p>";
}

/**
 * 判断对象是否为空
 */
$.isEmpty = function (obj) {
    return !obj || $.isEmptyObject(obj);
};


