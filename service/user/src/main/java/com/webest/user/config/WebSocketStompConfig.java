package com.webest.user.config;

import com.webest.user.presentation.interceptor.UserChatRoomJwtCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    private final UserChatRoomJwtCheck userChatRoomJwtCheck;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("socket/chat").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(userChatRoomJwtCheck);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 목적지 헤더가 /app으로 시작되는 STOMP 메시지는 다음으로 라우팅 된다.
        registry.setApplicationDestinationPrefixes("/app");

        // 목적지 헤더가 topic, queue로 시작되는 메시지를 브로커에게 라우팅한다.
        registry.enableSimpleBroker("/topic", "/queue");
    }
}
