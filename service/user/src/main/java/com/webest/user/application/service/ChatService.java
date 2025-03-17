package com.webest.user.application.service;

import com.webest.user.domain.chat.ChatRoom;
import java.util.Optional;

public interface ChatService {

    public Optional<ChatRoom> findChatroomByWriterAndReceiver(String writer, Long receiver);

    public ChatRoom saveChatLog(String chatData, String writer, Long receiver);

    public ChatRoom checkChatRoomExist(String writer, Long receiver);

}
