package com.eghm.websocket.config.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.eghm.websocket.model.User;
import com.eghm.websocket.utils.CommonConstant;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * 
 * @time 2016年5月16日
 * @author fanyin
 * websocket HttpSession拦截器
 */
public class SessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	

    @Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
			
	   if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
            	User user = (User) session.getAttribute(CommonConstant.SESSION_USER);
            	attributes.put(CommonConstant.SESSION_USER , user);
            	return true;
            }
        }
		return false;
	}
	
	
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
	}
	
}
