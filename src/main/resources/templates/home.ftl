<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>主界面</title>
<#include "resources.ftl">
<script src="${cxtPath}/js/home.js?t=201742114" type="text/javascript" charset="UTF-8"></script>
<script type="text/javascript">
	var workspaceId = ${workspace.id};
</script>
</head>
<body>
<input type="hidden" id="showHideDocument" value="${hidden!''}"/>
<input type="hidden" id="orderBy" value="${orderBy!''}"/>

<div class="navbar navbar-default navbar-fixed-top  navbar-info">
	<div id="navbar-responsive-collapse" class="collapse navbar-collapse">
    	<ul class="nav navbar-nav">
      		<li class="active"><a href="##">网站首页</a></li>
      		<li><a href="##">欢迎您:${loginUser.nickName}</a></li>
	 	</ul>
  </div>
</div>
<br>
<br>
<div>
	<div class="col-xs-2">
		<br>
		<div class="panel panel-default" >
			<div class="panel-heading">
				<a class="btn btn-info btn-block btn-sm ">添加工作空间</a>
			</div>
		<div class="panel-body" style="height: 640px;overflow:auto;" id="test">
			<div class="list-group">
			
				<#if workspaceList?? && workspaceList?size gt 0>
					<#list workspaceList as workList>
			   			 <a href="#" title="创建时间:${workList.createDate?string('yyyy-MM-dd')}" class="list-group-item  <#if workList.id == workspace.id>active <#else> list-group-item-warning </#if>">${workList.spaceName}<button class="close" type="button" onclick="$.deleteFun('','','确定要删除该工作空间吗?')">&times;</button></a>
					</#list>
				</#if>		
			</div>
		</div>
		</div>
	</div>
	<div class="col-xs-8">
	<br>
		<div class="panel panel-default">
			<div class="panel-heading">
					<div class="row">
						<div class="col-xs-2">
							<a href="#" class="btn btn-primary" onclick="$.showPrompt('请输入Word文档名称',createDocument,'word');">创建Word文档</a>
						</div>
						<div class="col-xs-2 ">
							<a href="#" class="btn btn-info" onclick="$.showPrompt('请输入PPT文档名称',createDocument,'ppt');">创建PPT文档</a>
						</div>
						<div class="col-xs-4 col-xs-push-4 search-document">
								<input type="text" class="form-control" id="documentName" placeholder="请输入文档名称"/>
								<input type="submit"  value="" onclick="searchDocument();"/>
						</div>
					</div>
			</div>
			<div class="panel-body" id="centerMenu" style="height: 640px;overflow:auto;">
					<div id="documentId" class="row">
						<!-- 如果request中wordList集合大于0 -->
						<#if documentList?? && documentList?size gt 0 >
							<#list documentList as dList>
								<div class="col-xs-2 underline">
										<a href="#" class="thumbnail documentMenu <#if dList.isShow == 1 > hidden-backgroud</#if>" >
										<img alt="${dList.type!'word'}" src="${cxtPath}/images/icon_${dList.type!'word'}.png" style="height: 100px;  display: block;" >
										<span>${dList.docName}</span>
										<input type="hidden" class="id"  value="${dList.id}" />
										<input type="hidden" class="type" value="${dList.type}" />
									</a>
								</div>
							</#list>
						<#else>
							<p id="noDocument" class="text-center text-muted center-vertical">暂无文档</p>						
						</#if>
					</div>
			</div>
	</div>
	</div>
	<div class="col-xs-2">
		<br>
		<div class="panel panel-default" >
		<div class="panel-heading">
			<a class="btn btn-info btn-block btn-sm ">添加好友</a>
		</div>
		<div class="panel-body" style="height: 640px;overflow:auto;">
			<div class="list-group">
				<#if friendList?? && friendList?size gt 0>
					<#list friendList as fList>
			   			 <a href="#" class="list-group-item list-group-item-warning">${fList.nickName}</a>
					</#list>
				</#if>	
			</div>
		</div>
		</div>
	</div>
</div>
<form action="" method="post" name="document" id="document">
	<input type="hidden" value="" id="docPassword" name="docPassword"/>
</form>
</body>

</html>