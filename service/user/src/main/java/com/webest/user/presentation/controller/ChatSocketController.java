package com.webest.user.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webest.user.application.service.ChatService;
import com.webest.user.presentation.dto.request.ChatRequestDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatSocketController {

    private final ChatService chatService;

    @MessageMapping("/chat/{roomNum}")
    @SendTo("/topic/chat/{roomNum}")
    public String handler(ChatRequestDto requestDto,
        @DestinationVariable("roomNum") String roomNum) throws JsonProcessingException {

        chatService.saveChatLog(requestDto.chatData(), requestDto.userId());

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
