package com.eghm.websocket.config.websocket;/*package com.eghm.websocket.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;


public class SystemWebSocketHandler implements WebSocketHandler {
	
	
	private static final List<WebSocketSession> users = new ArrayList<WebSocketSession>();
	
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		System.out.println("Server:cennected OK!");
		users.add(session);

	}

	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		TextMessage tm = new TextMessage(message.getPayload()+"");
		System.out.println(message.getPayload());
		session.sendMessage(tm);

	}

	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);
	}

	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		
		users.remove(session);
	}

	public boolean supportsPartialMessages() {
		
		return false;
	}
	
	*//**
	 * 给所有在线用户发送消息
	 * @param message
	 *//*
	public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : users) {
			if (user.isOpen()) {
				try {
					user.sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendMessageToUser(String username,TextMessage message) {
		for (WebSocketSession user : users) {
			if (user.getAttributes().get("username").equals(username)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
}
*/