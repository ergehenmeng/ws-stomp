$.loadMsg = function(msg){
	return parent.layer.msg(msg,{
			icon:16,
			time:0,
			shade:[0.4,'#000'],
			shift:5
			});
};
$.deleteFun = function(url,id,msg,data,callback,value){
	let sendData = $.extend({},data);
	layer.confirm(msg,{icon:3,shift:5},function(yes){
		sendData["id"] = id;
		$.post(url,sendData,function(data){
			if(data.result){
				$.right(data.msg);
				callback();
			}else{
				$.error(data.msg);
			}
		});
	});
};
/**
 * 显示创建文档的名称
 */
$.showPrompt = function(title,func,type,defaultValue){
	layer.prompt({
		formType:0,
		title:title,
		shift:5,
		maxlength:8,
		value:defaultValue
	},function(value,index,elem){
		layer.close(index);
		func(value,type);
	});
};

/**
 * 密码框,文本域
 * @param title 标题
 * @param type 类型 1:密码框 2文本域
 * @param func 回调函数
 * @param length 长度限制
 * @param data 附加数据
 */
$.password = function(title,type,func,length,data){
	layer.prompt({
		formType:type,
		title:title,
		shift:5,
		maxlength:length
	},function(value,index,elem){
		layer.close(index);
		func(value,data);
	});
};

$.right = function(msg){
	layer.alert(msg,{icon:1, shift:5});
};
$.error = function(msg){
	layer.alert(msg,{icon:5, shift:5});
};


$.window = function(html,title,callback){
	layer.open({
		title:title,
		content:html,
		shift:5,
		btn:["确定","取消"],
		yes:function(){
			callback();
		},
		cancel:function(index){
			layer.close(index);
		}
	});
};