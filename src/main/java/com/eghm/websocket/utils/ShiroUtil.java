package com.eghm.websocket.utils;

import com.eghm.websocket.enums.ErrorCode;
import com.eghm.websocket.exception.SystemException;
import com.eghm.websocket.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * @author 殿小二
 * @date 2020/11/4
 */
public class ShiroUtil {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static User getUser() {
        Object principal = getSubject().getPrincipal();
        if (principal != null) {
            return (User) principal;
        }
        throw new SystemException(ErrorCode.LOGIN_TIMEOUT);
    }

    public static Long getUserId() {
        return getUser().getId();
    }

    public static void setUser(User user) {
        Subject subject = getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        String realmName = principals.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        subject.runAs(newPrincipalCollection);
    }
}
