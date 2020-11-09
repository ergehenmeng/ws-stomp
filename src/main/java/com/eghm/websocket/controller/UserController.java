package com.eghm.websocket.controller;

import com.eghm.websocket.config.web.shiro.SystemPasswordToken;
import com.eghm.websocket.dto.RespBody;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
@RestController
public class UserController {

    /**
     * 登陆接口
     */
    @PostMapping("/login")
    public RespBody<Object> login(String mobile, String pwd, String captcha) {
        SystemPasswordToken token = new SystemPasswordToken();
        token.setUsername(mobile);
        token.setPassword(pwd.toCharArray());
        token.setCaptcha(captcha);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return RespBody.success();
        } catch (AuthenticationException e) {
            return RespBody.error(e.getMessage());
        }
    }
}
