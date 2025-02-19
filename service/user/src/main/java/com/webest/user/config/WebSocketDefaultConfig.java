package com.webest.user.config;

import com.webest.user.application.socket.DefaultSocketMyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//@Configuration
//@EnableWebSocket
public class WebSocketDefaultConfig implements WebSocketConfigurer {

    @Bean
    public WebSocketHandler myHandler() {
        return new DefaultSocketMyHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/chat_socket");

    }

}
