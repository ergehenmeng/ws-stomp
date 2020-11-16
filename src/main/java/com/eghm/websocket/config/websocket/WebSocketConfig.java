package com.eghm.websocket.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @author 殿小二
 * @date 2020/11/9
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // websocket链接地址
        registry.addEndpoint("/serverChat").withSockJS().setInterceptors(sessionHandshakeInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 客户端发送的消息前缀
        registry.setApplicationDestinationPrefixes("/websocket");
        registry.enableSimpleBroker("");
    }

    @Bean
    public HandshakeInterceptor sessionHandshakeInterceptor() {
        return new SessionHandshakeInterceptor();
    }
}
