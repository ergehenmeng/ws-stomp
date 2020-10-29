var stompClient = null;
var load = null;
var ueditor = null;
var initDocument = null;
var documentClient = null;

$(function(){
	ueditor = UE.getEditor("container");
	load = $.loadMsg("服务器连接中...");
	connect();
	$("#chatMessage").on("keypress",function(event){
		if(event.keyCode == "13"){
			sendMessage();
		}
	});
});

window.onbeforeunload = function(event){
	return test();
}
function test(){
	console.log(initDocument);
	if(initDocument)initDocumen.unsubscribe();
	if(documentClient)documentClient.unsubscribe();
}

/**
 * websocket首次建立连接
 */
function connect(){
	var socket = new SockJS("/coordination");
	stompClient = Stomp.over(socket);
	stompClient.connect("","",function(frame){
		console.log("websocket链接成功:" + frame);
		if(load != null ) layer.close(load);
		initDocuemntEdit();
		documentSubscribe();
	},function(err){
		console.log("websocket链接失败:" + err);
		connect();
	});
}



/**
 * 全局订阅聊天消息和编辑器消息内容
 * 订阅消息时,可以在服务器端@SubscribeMapping来相应消息,与HTTP类似,请求一次相应一次(仅仅执行一次,但与HTTP区别是该方法是异步的) 
 * {
 * type:1,
 * content:content
 * }
 *
 */
function documentSubscribe(){
	documentClient = stompClient.subscribe("/document/" + workspaceId + "/" + documentId,function(data){
		var result = JSON.parse(data.body);
		console.log(result);
		if(!$.isEmptyObject(result)){
			var type = result.type;
			switch(type){
				case 1://聊天信息内容
					showChat(result);
				break;
				case 2://编辑器内容同步
					updateContent(result);
				break;
				
			}
		}
	});
}

/**
 * 初始化编辑器内容 通过/app过滤直接调用初始化方法
 * @param client
 * @param doucmentId 文档id
 * @param fileId 文档中页Id
 * @return data 返回格式 json
 */
function initDocuemntEdit(){
	initDocument = stompClient.subscribe("/app/initDocument/" + workspaceId + "/" + documentId,function(data){
		var documentResult = JSON.parse(data.body);
		if(!$.isEmpty(documentResult) && !$.isEmpty(documentResult.document)){
			loadLeftFileList(documentResult.document.documentFiles);
			var read = (documentResult.document.userId == userId) ? true : false;
			loadCenterFile(documentResult.document.documentFiles[0],read);
			forEachChat(documentResult.chat);
		}
	});
}
/**
 * 加载左侧列表
 * @param obj 要加载的列表 Array
 */
function loadLeftFileList(obj){
	$.each(obj,function(i,v){
		if(i == 0 ){
			$("#leftFileList").append('<div class="col-xs-12"><a class="thumbnail selectActive" onclick="changeFile(this,' + v.id + ');" href="javascript:void(0)"><img alt="" src="/images/ppt_fanye.png" style="height: 100px;  display: block;"></a></div>');
			$("#fileId").val(v.id);
		}else{
			$("#leftFileList").append('<div class="col-xs-12"><a class="thumbnail" onclick="changeFile(this,' + v.id + ');" href="javascript:void(0)"><img alt="" src="/images/ppt_fanye.png" style="height: 100px;  display: block;"></a></div>');
		}
	});
}

/**
 * 添加事件监听
 */
function addEvent(){
	ueditor.addListener("contentChange",sendContentMsg);
}
function removeEvent(){
	ueditor.removeListener("contentChange",sendContentMsg);
}

function sendContentMsg(){
	var content = ueditor.getContent();
	sendContent($("#fileId").val(),content);
}

/**
 * 切换文档页事件
 * @param id
 */
function changeFile(obj,id){
	var fileId = $("#fileId").val();
	if(fileId != id){
		$("#fileId").val(id);
		$.post("/changeFile",{"id":id},function(data){
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
 */
	
function loadCenterFile(obj,enable){
	ueditor.setContent(obj.content);
	if(enable){
		ueditor.setEnabled();
		addEvent();
	}else{
		ueditor.setDisabled(["fullscreen","preview"]);
	}
	
}



/**
 * 订阅文档编辑空间(中)
 * @param client
 */
function updateContent(result){
	var fileId = $("#fileId").val();
	if(userId != result.userId && fileId == result.id){
		removeEvent();
		ueditor.setContent(result.content);
		addEvent();
	}
}


/**
 * 循环遍历输出聊天信息
 * @param data
 */
function forEachChat(data){
	if(! $.isEmpty(data)){
		$.each(data,function(i,v){
			showChat(v);
		});
	}

}

/**
 * 发送和接收聊天信息
 */
function sendMessage(){
	var msg = $.trim($("#chatMessage").val());
	if(msg){
		stompClient.send("/app/userChat",{},JSON.stringify({
	        'chatContent': encodeURIComponent(msg),
	        'documentId': documentId,
	        'workspaceId':workspaceId
	    }));
	}
	$("#chatMessage").focus();
}
/**
 * 发送文档内容
 * @param id 
 * @param msg 文档内容
 */
function  sendContent(id,msg){
	stompClient.send("/app/documentChange", {},JSON.stringify({
        'content': encodeURIComponent(msg),
        'id': id,
        "documentId":documentId,
        "workspaceId":workspaceId
        
    }));
}


/**
 * 显示聊天信息
 * @param data 后台返回的信息json
 */
function showChat(data){
	var active = "p_noactive";
	if(data.id == userId){
		active = "p_active";
		$("#chatMessage").val("");
	} 
	$("#chatRoom").append(contentHtml(data.nickName,data.createTime,data.chatContent,active));
	var div = document.getElementById("chatRoom");
	div.scrollTop = div.scrollHeight; 
}


/**
 * 组装聊天信息
 * @param name 发言人
 * @param msg 发言内容
 * @param active 状态
 * @returns {String}
 */
function contentHtml(name,time,msg,active){
	return "<p class='" + active + "'>" + name + " " + time + "</p>" + "<p class='chatContent'>" + msg + "</p>";
}
/**
 * 判断对象是否为空
 */
$.isEmpty = function(obj){
	if(obj == null || obj == "" || $.isEmptyObject(obj)){
		return true;
	}
	return false;
};


