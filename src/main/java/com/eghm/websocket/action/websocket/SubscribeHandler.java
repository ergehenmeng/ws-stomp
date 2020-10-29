package com.eghm.websocket.action.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

public class SubscribeHandler implements ApplicationListener<SessionSubscribeEvent>{

	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		String subscriptionId = SimpMessageHeaderAccessor.getSubscriptionId(headers);
		String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
		System.out.println("subscriptionId------------------------:" + subscriptionId);
		System.out.println("sessionId-----------------------------:" + sessionId);
	}

}
