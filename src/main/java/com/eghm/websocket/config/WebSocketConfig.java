package com.eghm.websocket.config;/*package com.eghm.websocket.config;


import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;



public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer  implements WebSocketConfigurer{
	
	private SystemWebSocketHandler systemWebSocketHandler;
	
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		registry.addHandler(systemWebSocketHandler, "/echo").addInterceptors(new HandshakeInterceptorImpl());//支持websocket访问链接
		
		registry.addHandler(systemWebSocketHandler,"/sockjs/echo").addInterceptors(new HandshakeInterceptorImpl()).withSockJS(); //不支持websocket的访问链接
	}

	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		
	}
	
}
*/