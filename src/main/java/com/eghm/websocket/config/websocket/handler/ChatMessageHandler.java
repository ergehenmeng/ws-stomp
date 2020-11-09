package com.eghm.websocket.config.websocket.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class ChatMessageHandler implements WebSocketHandler {

    private static final ArrayList<WebSocketSession> users = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        String userName = (String) session.getAttributes().get("webSocketName");
        log.error(" session Attribute: " + session.getAttributes());
        log.error("用户登陆: " + userName);
        users.add(session);
        if (userName != null) {
            sendMessageToUsers(new TextMessage(userName + " 进入聊天室"));
            session.sendMessage(new TextMessage("当前在线人数: " + users.size() + " 进入聊天室"));
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        sendMessageToUsers(message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        log.debug("链接出错，关闭链接......");
        users.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.error("链接关闭......" + closeStatus.toString());
        users.remove(session);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessageToUsers(WebSocketMessage<?> message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("webSocketName").equals(userName)) {
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
