package com.eghm.websocket.config.websocket.handler;


import com.eghm.websocket.model.User;
import com.eghm.websocket.utils.CommonConstant;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.Map;



public class ConnectHandler implements ApplicationListener<SessionConnectEvent> {
	
	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		System.out.println("接入了.................................");
		MessageHeaders headers =  event.getMessage().getHeaders();
		Map<String, Object> attribute = SimpMessageHeaderAccessor.getSessionAttributes(headers);
		User user = (User)attribute.get(CommonConstant.SESSION_USER);
		String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
		CommonConstant.WEBSOCKET_SESSION.put(sessionId, user);
	}
}
