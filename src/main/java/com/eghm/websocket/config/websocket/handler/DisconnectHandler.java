package com.eghm.websocket.config.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * @author 二哥很猛
 */
@Slf4j
public class DisconnectHandler implements ApplicationListener<SessionDisconnectEvent>{
	
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		log.info("断开链接 [{}]", event.getSessionId());
	}
}
