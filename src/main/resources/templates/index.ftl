<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>在线教育系统</title>
    <meta name="description" content="User login page"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/static/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="/static/css/ace-fonts.css"/>
    <link rel="stylesheet" href="/static/css/ace.min.css"/>
    <link rel="stylesheet" href="/static/css/ace-rtl.min.css"/>
</head>
<body class="login-layout light-login">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="center" style="margin-top: 30%;"></div>
                    <div class="space-6"></div>
                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger"></h4>
                                    <form method="post">
                                        <fieldset>
                                            <label class="block clearfix">
                                                <span class="block input-icon input-icon-right">
                                                    <input type="text" name="userName" id="userName"
                                                           class="form-control" placeholder="用户名、手机号、邮箱"/><i
                                                            class="ace-icon fa fa-user"></i>
												</span>
                                            </label>
                                            <label class="block clearfix">
                                                <span class="block input-icon input-icon-right">
                                                    <input type="password" name="password" id="password"
                                                           class="form-control" placeholder="密码"/> <i
                                                            class="ace-icon fa fa-lock"></i>
												</span>
                                            </label>
                                            <div class="text-center" style="height: 25px;">
                                                <span style="color: red;" id="tipMsg"></span>
                                            </div>
                                            <div class="clearfix">
                                                <label class="inline">
                                                    <input type="checkbox" class="ace" id="rememberMe"/><span class="lbl">记住我</span>
                                                </label>
                                                <button type="button" id="login"
                                                        class="width-35 pull-right btn btn-sm btn-primary">
                                                    <i class="ace-icon fa fa-key"></i> <span
                                                            class="bigger-110">登陆</span>
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="/static/js/jquery.min.js" type="text/javascript"></script>
<script src="/static/js/md5.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $(document).on("click", "#login", function (e) {
            let userName = $("#userName").val();
            let password = $("#password").val();
            if (!userName || !password) {
                $("#tipMsg").text("用户名或密码不能为空");
            }
            $.post("/login", {
                "userName": userName,
                "password": md5(password),
                "rememberMe": $("#rememberMe").prop('checked')
            }, function (data) {
                if (data.result) {
                    $("#password").val("");
                    window.location.href = "/home";
                } else {
                    $("#tipMsg").text(data.msg);
                }
            }, "json");
        });
    });
</script>
</html>
