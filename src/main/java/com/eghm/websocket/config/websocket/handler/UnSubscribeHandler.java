package com.eghm.websocket.config.websocket.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

public class UnSubscribeHandler implements ApplicationListener<SessionUnsubscribeEvent> {

	@Override
	public void onApplicationEvent(SessionUnsubscribeEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		String subscriptionId = SimpMessageHeaderAccessor.getSubscriptionId(headers);
		String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
		System.out.println("subscriptionId取消订阅------------------------:" + subscriptionId);
		System.out.println("sessionId取消订阅-----------------------------:" + sessionId);
	}

}
