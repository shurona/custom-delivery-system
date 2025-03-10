package com.webest.user.application.service;

import com.webest.user.domain.model.User;
import com.webest.user.domain.repository.UserRepository;
import com.webest.user.exception.UserErrorCode;
import com.webest.user.exception.UserException;
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

    @Override
    public void saveChatLog(String chatData, String writer, Long receiver) {

        log.info("[찾을 것]: {}", writer);
        User user = userByIdAndCheck(writer);
        log.info("[작성자] 유저 아이디 : {}, 유저 이름 : {}", user.getId(), user.getUserName());

        log.info("[채팅 로그 기록] 유저 닉네임 : {} 내역 : {}", receiver, chatData);
    }

    private User userByIdAndCheck(String username) {
        return userRepository.findByUserId(username).orElseThrow(() ->
            new UserException(UserErrorCode.USER_NOT_FOUND)
        );
    }
}
