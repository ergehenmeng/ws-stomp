package com.eghm.websocket.config.websocket;

import com.eghm.websocket.constant.SocketConstant;
import com.eghm.websocket.utils.ShiroUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @author 二哥很猛
 */
public class SessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        attributes.put(SocketConstant.SOCKET_USER , ShiroUtil.getUser());
        return true;
	}
	

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
	}
	
}
