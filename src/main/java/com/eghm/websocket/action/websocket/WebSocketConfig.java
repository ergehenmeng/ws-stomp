package com.eghm.websocket.action.websocket;



import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Administrator
 * 该方式是基于注解形式的 支持 订阅,转发,广播等
 * implement WebSocketConfigurer 为原生的websocket 需要自定义handler来处理相关数据
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
        //订阅接口前缀
        //客户端发送消息必须以/app开头
		registry.enableSimpleBroker("/document");
		registry.setApplicationDestinationPrefixes("/app");
	}

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

    }

    protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/coordination").withSockJS().setInterceptors(new SessionHandshakeInterceptor());
	}

}

