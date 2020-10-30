package com.eghm.websocket.config.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;




import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class HandshakeInterceptorImpl extends HttpSessionHandshakeInterceptor{
	
	private static final String WEB_SOCKET_NAME = "webSocketName";
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		if (request instanceof ServletServerHttpRequest) {
			
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession();
			if(session != null){
				String userName = (String) session.getAttribute(WEB_SOCKET_NAME);
				attributes.put(WEB_SOCKET_NAME, userName);
			}
		}
		return true;
	}

	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		
		
	}

}
