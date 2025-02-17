package com.webest.user.presentation.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public String handler(String greeting) {
        System.out.println("?? : " + " 여기 들어오나요? : " + greeting);
        return "[" + getTimestamp() + ": " + greeting + "]";
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
