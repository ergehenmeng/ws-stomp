package com.eghm.websocket.config.websocket.handler;


import com.eghm.websocket.utils.CommonConstant;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;



public class DisconnectHandler implements ApplicationListener<SessionDisconnectEvent>{
	
	
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		String key = event.getSessionId();
		CommonConstant.WEBSOCKET_SESSION.remove(key);
		System.out.println("用户退出登陆----------------------------");
	}

}
