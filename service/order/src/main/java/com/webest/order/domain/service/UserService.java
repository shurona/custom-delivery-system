package com.webest.order.domain.service;

import com.webest.order.infrastructure.client.user.UserClient;
import com.webest.order.infrastructure.client.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient userClient;

    public UserResponse getUser(String userId) {
       return userClient.getUser(userId).getData();
    }

}
