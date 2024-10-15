package com.webest.order.infrastructure.client.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(String userId,
                           String userName,
                           String email,
                           Long addressCode,
                           String detailAddress) {

}
