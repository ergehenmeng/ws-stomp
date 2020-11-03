package com.eghm.websocket.config.web.shiro;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
@Getter
@Setter
public class SystemPasswordToken extends UsernamePasswordToken {

    /**
     * 验证码
     */
    private String captcha;
}
