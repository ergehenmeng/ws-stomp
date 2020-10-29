package com.eghm.websocket.action.websocket;


import com.eghm.websocket.utils.Constants;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;



public class DisconnectHandler implements ApplicationListener<SessionDisconnectEvent>{
	
	
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		String key = event.getSessionId();
		Constants.WEBSOCKET_SESSION.remove(key);
		System.out.println("用户退出登陆----------------------------");
	}

}
