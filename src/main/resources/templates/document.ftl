<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<title>文档编辑页面</title>
<#include "resources.ftl">
<script type="text/javascript" src="/static/js/document.js?t=201703021188"></script>
<script type="text/javascript">
	var workspaceId = ${workspaceId!};
	var documentId = ${documentId!};
	var userId = ${loginUser.id};
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
			<div class="panel-body" style="height: 620px;overflow:auto;" id="leftFileList">
				
			</div>
			<div class="panel-footer">
				<div class="btn-group btn-group-justified">
					<a class="btn btn-default " title="添加幻灯片" href="javascript:void(0)" onclick="connect();"><span class="glyphicon glyphicon-plus"></span></a>
					<a class="btn btn-default" title="播放幻灯片"><span class="glyphicon glyphicon-play-circle"></span></a>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-7" >
		<br>
		<!-- 加载编辑器的容器 -->
		<script id="container" name="content" type="text/plain" style="height: 590px;"></script>
		<input type="hidden" id="fileId" />
	</div>
	
	<div class="col-xs-3">
		<br>
		<div class="panel panel-success" >
				<div class="panel-heading">
					<span class="text-center">聊天室</span>
				</div>
		    	<div class="panel-body" id="chatRoom" style="height: 620px;overflow:auto;">
		    		
				</div>
				<div class="panel-footer">
					<div class="input-group">
						<input type="text" id="chatMessage" class="form-control" placeholder="输入信息"  maxlength="20"/>
  						<span class="input-group-btn" id="basic-addon2" ><a class="btn btn-info" href="javascript:void(0)"  onclick="sendMessage();">发送</a></span>
					</div>
				</div>
		</div>
	</div>
</div>

</body>
</html>