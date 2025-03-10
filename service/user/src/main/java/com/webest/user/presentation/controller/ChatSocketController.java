package com.webest.user.presentation.controller;

import com.webest.user.application.service.ChatService;
import com.webest.user.exception.UserErrorCode;
import com.webest.user.exception.UserException;
import com.webest.user.infrastructure.handler.SocketEventHandler;
import com.webest.user.presentation.dto.request.ChatRequestDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatSocketController {

    private final ChatService chatService;
    private final SocketEventHandler socketEventHandler;


    @MessageMapping("/chat/{storeId}")
    @SendTo("/topic/chat/{storeId}")
    public String handler(ChatRequestDto requestDto,
        SimpMessageHeaderAccessor accessor,
        @DestinationVariable("storeId") String storeId) {

        String userId = socketEventHandler.getUserIdBySession(accessor.getSessionId());

        // userId가 없는 경우 세션 생성에 실패한 것이다.
        if (userId == null) {
            throw new UserException(UserErrorCode.SOCKET_SESSION_UNKNOWN);
        }

        // 여기서 인증 정보를 갖고 온다.
        chatService.saveChatLog(
            requestDto.chatData(), userId, requestDto.userId());

        return "[" + getTimestamp() + ": " + requestDto + "]";
    }

    private String getTimestamp() {
        return new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date());
    }

    @MessageExceptionHandler
    public Exception handleException(Exception exception) {
        System.out.println("에러 발생 : " + exception.getCause());
        System.out.println("메시지 : " + exception.getMessage());
        return exception;
    }
}
