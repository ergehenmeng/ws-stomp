package com.eghm.websocket.config.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * @author 订阅事件
 */
@Slf4j
public class SubscribeHandler implements ApplicationListener<SessionSubscribeEvent>{

	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		String subscriptionId = SimpMessageHeaderAccessor.getSubscriptionId(headers);
		String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        log.info("订阅成功 sessionId:[{}] subscriptionId:[{}]", sessionId, subscriptionId);
	}
}
