<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>文档管理</title>
    <#include "resources.ftl">
    <meta name="renderer" content="webkit">
    <script src="/static/js/home.js?t=201742114" type="text/javascript" charset="UTF-8"></script>
    <script type="text/javascript">
        var spaceId = ${spaceId!};
    </script>
</head>
<body>
<div class="navbar navbar-default navbar-fixed-top  navbar-info">
    <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">网站首页</a></li>
            <li><a href="#">欢迎您:${nickName!}</a></li>
        </ul>
        <p class="navbar-text navbar-right"><a href="#" class="navbar-link" id="logout">退出</a>&nbsp;&nbsp;&nbsp;&nbsp;</p>
    </div>
</div>
<br>
<br>
<div>
    <div class="col-xs-2">
        <br>
        <div class="panel panel-default">
            <div class="panel-heading">
                <a class="btn btn-info btn-block btn-sm ">添加工作空间</a>
            </div>
            <div class="panel-body" style="height: 640px;overflow:auto;" id="test">
                <div class="list-group">

                    <#if spaceList?? && spaceList?size gt 0>
                        <#list spaceList as space>
                            <a href="#" title="创建时间:${space.addTime?string('yyyy-MM-dd')}"
                               class="list-group-item  <#if spaceId == space.id>active <#else> list-group-item-warning </#if>">${space.title}
                                <button class="close" type="button" onclick="$.deleteFun('','','确定要删除该工作空间吗?')">
                                    &times;
                                </button>
                            </a>
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
                    <div class="col-xs-4 col-xs-push-8 search-document">
                        <input type="text" class="form-control" id="documentName" placeholder="请输入文档名称"/>
                        <input type="submit" value="" onclick="searchDocument();"/>
                    </div>
                </div>
            </div>
            <div class="panel-body" id="centerMenu" style="height: 640px;overflow:auto;">
                <div id="documentId" class="row">
                    <!-- 如果request中wordList集合大于0 -->
                    <#if documentList?? && documentList?size gt 0 >
                        <#list documentList as document>
                            <div class="col-xs-2 underline">
                                <a href="#"
                                   class="thumbnail documentMenu <#if document.hidden == true > hidden-backgroud</#if>">
                                    <img alt="${document.type!'word'}" src="/static/images/${document.type!'word'}.png"
                                         style="height: 100px;  display: block;">
                                    <span>${document.docName}</span>
                                    <input type="hidden" class="id" value="${document.id}"/>
                                    <input type="hidden" class="type" value="${document.type}"/>
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
        <div class="panel panel-default">
            <div class="panel-heading">
                <a class="btn btn-info btn-block btn-sm ">添加好友</a>
            </div>
            <div class="panel-body" style="height: 640px;overflow:auto;">
                <div class="list-group">
                    <#if userList?? && userList?size gt 0>
                        <#list userList as user>
                            <a href="#"
                               class="list-group-item list-group-item-warning">${user.nickName} <#if user.id == userId>
                                    <span class="label label-success">我</span></#if> </a>
                        </#list>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>