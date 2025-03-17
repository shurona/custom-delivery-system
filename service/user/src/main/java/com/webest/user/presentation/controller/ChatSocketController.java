package com.webest.user.presentation.controller;

import com.webest.user.application.service.ChatService;
import com.webest.user.domain.chat.ChatRoom;
import com.webest.user.exception.UserErrorCode;
import com.webest.user.exception.UserException;
import com.webest.user.infrastructure.handler.SocketEventHandler;
import com.webest.user.presentation.dto.request.ChatRequestDto;
import com.webest.user.presentation.dto.response.ChatLogResponseDto;
import com.webest.user.presentation.dto.response.ChatRoomCreateResponseDto;
import com.webest.web.response.CommonResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class ChatSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final SocketEventHandler socketEventHandler;

    /*
        채팅방 생성
     */
    @PostMapping("/chat/{rcvUserId}")
    public CommonResponse<ChatRoomCreateResponseDto> createChatRoom(
        @PathVariable("rcvUserId") Long userId,
        @RequestHeader("X-UserId") String xUserId
    ) {
        ChatRoom chatRoom = chatService.checkChatRoomExist(xUserId, userId);
        return CommonResponse.success(new ChatRoomCreateResponseDto(chatRoom.getId()));
    }

    @MessageMapping("/chat")
//    @SendTo("/topic/chat/{rcvUserId}")
    public void handler(ChatRequestDto requestDto,
        SimpMessageHeaderAccessor accessor) {

        String writer = socketEventHandler.getUserIdBySession(accessor.getSessionId());

        // writer의 세션이 없는 경우 세션 생성에 실패한 것이다.
        if (writer == null) {
            throw new UserException(UserErrorCode.SOCKET_SESSION_UNKNOWN);
        }

        log.info("[채팅 유저] : {}, [입력 받은 유저] : {}", writer, requestDto.userId());

        // 채팅 로그를 저장한다.
        ChatRoom chatRoom = chatService.saveChatLog(
            requestDto.chatData(), writer, requestDto.userId());

        // 구독된 클라이언트에 전달
        String destination = "/topic/chat/" + chatRoom.getId();
        messagingTemplate.convertAndSend(destination,
            new ChatLogResponseDto(requestDto.chatData(), writer, LocalDateTime.now().toString()));
    }
    /*
        채팅창
     */

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
