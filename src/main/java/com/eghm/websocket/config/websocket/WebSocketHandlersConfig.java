package com.eghm.websocket.config.websocket;

import com.eghm.websocket.config.websocket.handler.ConnectHandler;
import com.eghm.websocket.config.websocket.handler.DisconnectHandler;
import com.eghm.websocket.config.websocket.handler.SubscribeHandler;
import com.eghm.websocket.config.websocket.handler.UnSubscribeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketHandlersConfig {
	
	@Bean
	public ConnectHandler connectHandler(){
		return new ConnectHandler();
	}
	
	@Bean
	public DisconnectHandler disconnectHandler(){
		return new DisconnectHandler();
	}
	
	@Bean
	public SubscribeHandler subscribeHandler(){
		return new SubscribeHandler();
	}
	
	@Bean
	public UnSubscribeHandler unSubscribeHandler(){
		return new UnSubscribeHandler();
	}
	
	
}
