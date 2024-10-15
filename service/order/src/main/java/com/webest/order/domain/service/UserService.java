package com.webest.order.domain.service;

import com.webest.order.presentation.response.UserResponse;

public interface UserService {
    UserResponse getUser(String userId);
}
