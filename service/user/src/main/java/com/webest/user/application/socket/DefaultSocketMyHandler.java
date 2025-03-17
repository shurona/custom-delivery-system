package com.webest.user.application.socket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class DefaultSocketMyHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
        throws Exception {

        String payload = message.getPayload();
        System.out.println(payload);

        session.sendMessage(new TextMessage("re : " + payload));
    }
}
