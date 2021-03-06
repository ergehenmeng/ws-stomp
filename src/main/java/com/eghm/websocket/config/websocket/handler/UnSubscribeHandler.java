package com.eghm.websocket.config.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

/**
 * @author 二哥很猛
 */
@Slf4j
public class UnSubscribeHandler implements ApplicationListener<SessionUnsubscribeEvent> {

	@Override
	public void onApplicationEvent(SessionUnsubscribeEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		String subscriptionId = SimpMessageHeaderAccessor.getSubscriptionId(headers);
		String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
		log.info("取消订阅 sessionId:[{}] subscriptionId:[{}]", sessionId, subscriptionId);
	}
}
