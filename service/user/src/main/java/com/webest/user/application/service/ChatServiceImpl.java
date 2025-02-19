package com.webest.user.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void saveChatLog(String chatData, Long userId) {
        log.info("[채팅 로그 기록] 유저 닉네임 : {} 내역 : {}", userId, chatData);
    }
}
