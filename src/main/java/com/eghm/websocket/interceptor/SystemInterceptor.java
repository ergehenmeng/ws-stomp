package com.eghm.websocket.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import com.eghm.websocket.model.User;
import com.eghm.websocket.utils.WebUtils;

public class SystemInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		User user = WebUtils.getUser(request);
		if(user != null){
			return true;
		}
		String uri = request.getServletPath();
		if(exclude(uri)){
			return true;
		}
		response.sendRedirect("/");
		return false;
	}
	
	private List<String> excludeUrls;

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	/**
	 * 过滤部分url不拦截 例如登陆,验证码图片等 注意:调用该方法前确保excludeUrls已经配置好
	 * 
	 * @param url
	 *            前台访问的uri
	 * @return true 表示在不拦截范围内
	 */
	public boolean exclude(String url) {
		boolean flag = false;
		if (StringUtils.isEmpty(url) || url.equals("/")) {
			flag = true;
		}
		for (String u : excludeUrls) {
			if (u.contains(url)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}
