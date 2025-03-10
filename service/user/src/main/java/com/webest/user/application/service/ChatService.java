package com.webest.user.application.service;

public interface ChatService {

    public void saveChatLog(String chatData, String writer, Long receiver);

}
