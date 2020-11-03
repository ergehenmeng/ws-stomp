package com.eghm.websocket.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 二哥很猛
 */
public class CookieUtil {

    /**
     * 获取cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equals(cookieName)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * 添加cookie
     */
    public static Cookie addCookie(HttpServletResponse response, String cookieName, String value, Integer maxAge, String domain) {
        Cookie cookie = new Cookie(cookieName, value);
        if (maxAge != null) {
            cookie.setMaxAge(maxAge);
        }
        if (StrUtil.isNotEmpty(domain)) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
        return cookie;
    }
}
