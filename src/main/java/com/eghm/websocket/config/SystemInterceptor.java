package com.eghm.websocket.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



public class SystemInterceptor implements HandlerInterceptor {
	
	private List<String> excludeUrls;
	
	
	/**
	 * 判断是否在对应过滤规则内
	 * @param url
	 * @return
	 */
	public boolean exclude(String url){
		boolean flag = false;
		if(StringUtils.isEmpty(url) || url.equals("/")){
			 flag = true;
		}
		for(String u : excludeUrls){
			if(u.contains(url)){
				flag = true;
				break;
			}
		}
		return flag;
	}


	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}


	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}


	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
