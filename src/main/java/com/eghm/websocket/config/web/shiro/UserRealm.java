package com.eghm.websocket.config.web.shiro;

import cn.hutool.core.util.StrUtil;
import com.eghm.websocket.exception.SystemException;
import com.eghm.websocket.model.User;
import com.eghm.websocket.service.UserService;
import com.eghm.websocket.utils.CommonConstant;
import com.eghm.websocket.utils.WebUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
public class UserRealm extends AuthorizingRealm {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SystemPasswordToken passwordToken = (SystemPasswordToken) token;
        Object attribute = WebUtil.getRequest().getAttribute(CommonConstant.AUTH_CODE);
        String captcha = passwordToken.getCaptcha();
        if (StrUtil.isEmpty(captcha) || !captcha.equals(attribute)) {
            throw new AuthenticationException("验证码输入错误");
        }
        User user;
        try {
            user = userService.login(passwordToken.getUsername(), new String(passwordToken.getPassword()));
        } catch (SystemException e) {
            throw new AuthenticationException(e.getMessage(), e);
        }
        return new SimpleAuthenticationInfo(user, passwordToken.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        info.addStringPermission("*:*:*");
        return info;
    }
}
