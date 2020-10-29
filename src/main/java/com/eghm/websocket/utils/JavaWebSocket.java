package com.eghm.websocket.utils;
/*package com.eghm.websocket.action;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket")
public class JavaWebSocket {
	
	private volatile static int onlineCount = 0;
	
	private static CopyOnWriteArraySet<WebSocket> webSockets =  new CopyOnWriteArraySet<WebSocket>();
	
	private Session session;
	
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		webSockets.add(this);
		addOnlineCount();
		System.out.println("有链接进来,当前总人数: " + getOnlineCount());
	}
	
	@OnClose
	public void onClose( ){
		webSockets.remove(this);
		subOnlineCount();
		System.out.println("用户断开链接,当前总人数: " + getOnlineCount());
	}
	
	@OnMessage
	public void onMessage(String message ,Session session){
		for(WebSocket socket : webSockets){
			try {
				socket.sendMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	
	
	private void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	private static synchronized void subOnlineCount() {
		onlineCount -- ;
	}

	@OnError
	public void onError(Session session ,Throwable e)throws Exception{
		System.out.println("发生错误了...");
		e.printStackTrace();
	}
	
						
	

	private static synchronized int getOnlineCount() {
		return onlineCount;
	}

	private static synchronized void addOnlineCount() {
		WebSocket.onlineCount ++ ;
	}
}
*/