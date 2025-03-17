package com.webest.user.application.service;

import com.webest.user.domain.chat.ChatLog;
import com.webest.user.domain.chat.ChatRoom;
import com.webest.user.domain.model.User;
import com.webest.user.domain.repository.ChatRepository;
import com.webest.user.domain.repository.UserRepository;
import com.webest.user.exception.UserErrorCode;
import com.webest.user.exception.UserException;
import com.webest.web.common.UserRole;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatServiceImpl implements ChatService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;


    /*
        채팅창 조회
     */
    public Optional<ChatRoom> findChatroomByWriterAndReceiver(String writer, Long receiver) {
        User writerUser = userByUserIdAndCheck(writer);
        User rcvUser = userByIdAndCheck(receiver);

        // 두 사용자 중 한 명은 반드시 OWNER, 다른 한 명은 USER여야 함
        if (!((writerUser.getRole() == UserRole.OWNER && rcvUser.getRole() == UserRole.USER)
            || (writerUser.getRole() == UserRole.USER && rcvUser.getRole() == UserRole.OWNER))) {
            throw new UserException(UserErrorCode.ILLEGAL_COMBINATION_CHATROOM);
        }

        User owner = writerUser.getRole() == UserRole.OWNER ? writerUser : rcvUser;
        User participant = writerUser.getRole() == UserRole.OWNER ? rcvUser : writerUser;

        return chatRepository.findByRoomOwnerAndParticipant(
            owner, participant);
    }

    /*
        채팅 로그 저장
     */
    @Override
    @Transactional
    public ChatRoom saveChatLog(String chatData, String writer, Long receiver) {
        User writerUser = userByUserIdAndCheck(writer);

        // 만약 채팅창이 존재하지 않으면 멈춘다.
        ChatRoom chatRoom = findChatroomByWriterAndReceiver(writer, receiver).orElseThrow(
            () -> new UserException(UserErrorCode.CHATROOM_NOT_EXIST)
        );

        ChatLog.from(chatData, chatRoom, writerUser);

        return chatRoom;

    }

    /*
        채팅방이 존재하는 지 확인한다.
     */
    @Override
    @Transactional
    public ChatRoom checkChatRoomExist(String writer, Long receiver) {
        User writerUser = userByUserIdAndCheck(writer);
        User rcvUser = userByIdAndCheck(receiver);

        // 두 사용자 중 한 명은 반드시 OWNER, 다른 한 명은 USER여야 함
        if (!((writerUser.getRole() == UserRole.OWNER && rcvUser.getRole() == UserRole.USER)
            || (writerUser.getRole() == UserRole.USER && rcvUser.getRole() == UserRole.OWNER))) {
            throw new UserException(UserErrorCode.ILLEGAL_COMBINATION_CHATROOM);
        }

        User owner = writerUser.getRole() == UserRole.OWNER ? writerUser : rcvUser;
        User participant = writerUser.getRole() == UserRole.OWNER ? rcvUser : writerUser;

        Optional<ChatRoom> roomCheck = chatRepository.findByRoomOwnerAndParticipant(
            owner, participant);

        // 만약 아직 안열려있으면
        return roomCheck.orElseGet(() -> chatRepository.save(
            ChatRoom.from("방제를 어떻게 할까", owner, participant)));
    }

    /*
        Private Method
     */
    private User userByUserIdAndCheck(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() ->
            new UserException(UserErrorCode.USER_NOT_FOUND)
        );
    }

    private User userByIdAndCheck(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new UserException(UserErrorCode.USER_NOT_FOUND)
        );
    }
}
