package com.eghm.websocket.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	
	/**
	 * 获取cookie
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request,String cookieName){
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length>0){
			for(Cookie c : cookies){
				if(c.getName().equals(cookieName)){
					return c;
				}
			}
		}
		return null;
	}
	
	/**
	 * 添加cookie
	 * @param request
	 * @param response
	 * @param cookieName cookie名称
	 * @param maxAge cookie有效时间
	 * @param domain 跨域站点名称(".baidu.com")
	 */
	public static Cookie addCookie(HttpServletRequest request,HttpServletResponse response,String cookieName,String value,Integer maxAge,String domain){
		Cookie cookie = new Cookie(cookieName, value);
		if(maxAge != null){
			cookie.setMaxAge(maxAge);
		}
		if(StrUtil.isNotEmpty(domain)){
			cookie.setDomain(domain);
		}
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isEmpty(ctx) ? "/" : ctx);
		response.addCookie(cookie);
		return cookie;
	}
}
