package com.eghm.websocket.config.websocket.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.Map;


/**
 * @author 二哥很猛
 */
@Slf4j
public class ConnectHandler implements ApplicationListener<SessionConnectEvent> {
	
	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
        MessageHeaders headers =  event.getMessage().getHeaders();
        Map<String, Object> attribute = SimpMessageHeaderAccessor.getSessionAttributes(headers);
	    log.info("链接成功 headers:[{}]", attribute);
	}
}
