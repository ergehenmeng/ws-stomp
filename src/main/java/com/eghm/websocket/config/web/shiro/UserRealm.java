package com.eghm.websocket.config.web.shiro;

import cn.hutool.core.util.StrUtil;
import com.eghm.websocket.exception.SystemException;
import com.eghm.websocket.model.User;
import com.eghm.websocket.service.UserService;
import com.eghm.websocket.utils.CommonConstant;
import com.eghm.websocket.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    private UserService userService;

    /**
     * 是否开启验证码验证
     */
    @Value("${project.captcha:false}")
    private Boolean checkCaptcha;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SystemPasswordToken passwordToken = (SystemPasswordToken) token;
        Object attribute = WebUtil.getRequest().getAttribute(CommonConstant.AUTH_CODE);
        String captcha = passwordToken.getCaptcha();
        boolean check = checkCaptcha && (StrUtil.isEmpty(captcha) || !captcha.equals(attribute));
        if (check) {
            throw new AuthenticationException("验证码输入错误");
        }
        try {
            User user = userService.login(passwordToken.getUsername(), new String(passwordToken.getPassword()));
            this.removeOtherSession(user.getId());
            return new SimpleAuthenticationInfo(user, passwordToken.getPassword(), getName());
        } catch (SystemException e) {
            throw new AuthenticationException(e.getMessage(), e);
        }
    }

    private void removeOtherSession(Long userId) {
        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
        DefaultSessionManager sessionManager = (DefaultSessionManager)securityManager.getSessionManager();
        SessionDAO sessionDAO = sessionManager.getSessionDAO();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            SimplePrincipalCollection principalCollection = (SimplePrincipalCollection )session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principalCollection == null) {
                continue;
            }
            User principal = (User) principalCollection.getPrimaryPrincipal();
            // 用户一样,但session不一样
            if (principal.getId().equals(userId) && !session.getId().equals(sessionId)) {
                sessionDAO.delete(session);
                log.info("强制下线 sessionId:[{}]", session.getId());
                return;
            }
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        info.addStringPermission("*:*:*");
        return info;
    }
}
