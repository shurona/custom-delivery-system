package com.webest.user.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.webest.user.application.socket.DefaultSocketMyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@ExtendWith(MockitoExtension.class)
class SocketDefaultSocketMyHandlerTest {

    private DefaultSocketMyHandler defaultSocketMyHandler;
    private WebSocketSession session;

    @BeforeEach
    void setUp() {
        defaultSocketMyHandler = new DefaultSocketMyHandler();
        session = mock(WebSocketSession.class); // WebSocketSession을 Mock으로 생성
    }

    @Test
    void testHandleTextMessage() throws Exception {
        // Given: 클라이언트가 보낸 메시지
        TextMessage message = new TextMessage("Hello WebSocket!");

        // When: WebSocket 핸들러가 메시지를 처리할 때
        defaultSocketMyHandler.handleTextMessage(session, message);

        // Then: 서버가 응답 메시지를 클라이언트에게 보냈는지 검증
        verify(session, times(1)).sendMessage(new TextMessage("re : Hello WebSocket!"));
    }

}