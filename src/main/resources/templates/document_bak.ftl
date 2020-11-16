<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<title>文档编辑页面</title>
<#include "resources.ftl">
<script src="/static/js/sockjs.min.js" type="text/javascript" charset="UTF-8"></script>
<script src="/static/js/stomp.min.js" type="text/javascript" charset="UTF-8"></script>
<script type="text/javascript" src="/static/js/wangEditor.min.js?t=201703021188"></script>
<script type="text/javascript" src="/static/js/document.js?t=201703021188"></script>
<script type="text/javascript">
	let spaceId = ${spaceId!};
    let documentId = ${documentId!};
    let userId = '${userId}';
</script>
</head>
<body>
<div>
	<div class="col-xs-2">
		<br>
		<div class="panel panel-info" >
			<div class="panel-heading">
				<span class="text-center">幻灯片列表</span>
			</div>
			<div class="panel-body" style="height: 720px;overflow:auto;" id="leftFileList">
			</div>
			<div class="panel-footer">
				<div class="btn-group btn-group-justified">
					<a class="btn btn-default " title="添加幻灯片" href="javascript:void(0)" onclick="connect();"><span class="glyphicon glyphicon-plus"></span></a>
					<a class="btn btn-default" title="播放幻灯片"><span class="glyphicon glyphicon-play-circle"></span></a>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-8" >
		<br>
		<div id="wangEditorDiv"></div>
		<input type="hidden" id="fileId" />
	</div>
	
	<div class="col-xs-2">
		<br>
		<div class="panel panel-success" >
				<div class="panel-heading">
					<span class="text-center">聊天室</span>
				</div>
		    	<div class="panel-body" id="chatRoom" style="height: 720px;overflow:auto;">
		    		
				</div>
				<div class="panel-footer">
					<div class="input-group">
						<input type="text" id="chatMessage" class="form-control" placeholder="输入信息"  maxlength="20"/>
  						<span class="input-group-btn" id="basic-addon2" ><a class="btn btn-info" href="javascript:void(0)"  onclick="sendMsg();">发送</a></span>
					</div>
				</div>
		</div>
	</div>
</div>

</body>
</html>