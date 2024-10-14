package com.webest.order.domain.service;


import com.webest.order.presentation.response.UserResponse;
import org.springframework.web.bind.annotation.RequestHeader;

public interface UserService {
    UserResponse getUser(String userId);
}
