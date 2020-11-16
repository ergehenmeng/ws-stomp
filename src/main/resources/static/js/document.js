let load = null;
let stompClient;
let systemEditor;
$(function () {
    systemEditor = initEditor();
    load = $.loadMsg("服务器连接中...");
    connectServer("/serverChat", 1);
    $("#chatMessage").on("keypress", function (event) {
        if (event.keyCode === 13) {
            sendMsg();
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
    subscribe("/websocket/document/" + spaceId + "/" + documentId, function (json) {
        let action = json.action;
        if (action === 'SYNC_CONTENT') {
            systemEditor.txt.html(json.data.content);
        }
    });
};


let showContent = function (json) {
    if (json.userId === userId) {
        systemEditor.enable();
    } else {
        systemEditor.disable();
    }
    systemEditor.txt.html(json.content)
};

/**
 * 初始化聊天信息
 * 订阅编辑信息
 */
let initChat = function() {
    subscribe("/websocket/chatRoom/" + spaceId + "/" + documentId, function (json) {
        let action = json.action;
        switch (action) {
            case 'SUBSCRIBE_CHAT':
                // 聊天信息内容
                forEachChat(json.data);
                break;
            case 'CHAT_MSG':
                // 编辑器内容同步
                showChat(json.data);
                break;
            default: break;
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
        stompClient.subscribe(subscribeUrl, function (response) {
            // body为stomp的body体
            console.log("subscribeResp:" + response.body);
            if (typeof callback === "function" ) {
                let jsonResult = JSON.parse(response.body);
                callback(jsonResult);
            }
        });
    } else {
        console.error("subscribe失败,client未初始化");
    }
};




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
    let $chatMessage = $("#chatMessage");
    let msg = $.trim($chatMessage.val());
    if (msg) {
        send("/websocket/sendGroupMsg", {
            'content': encodeURIComponent(msg),
            'documentId': documentId,
            'spaceId': spaceId
        });
    }
    $chatMessage.focus();
}

/**
 * 发送文档内容
 * @param content 文档内容
 */
function updateContent(content) {
    send("/websocket/updateContent",{
        'content': encodeURIComponent(content),
        "documentId": documentId
    })
}

/**
 * 发送websocket信息到后天
 * @param sendUrl 发送地址
 * @param json 发送的内容 json
 */
let send = function(sendUrl, json) {
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
    if (data.userId === userId) {
        active = "p_active";
        $("#chatMessage").val("");
    }
    $("#chatRoom").append(contentHtml(data.nickName, data.createTime, data.content, active));
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


/**
 * 初始化编辑器
 */
function initEditor() {
    const E = window.wangEditor;
    const editor = new E('#wangEditorDiv');
    editor.config.height = 778;
    editor.config.onchangeTimeout = 500;
    editor.config.onchange = updateContent;
    editor.create();
    return editor;
}

