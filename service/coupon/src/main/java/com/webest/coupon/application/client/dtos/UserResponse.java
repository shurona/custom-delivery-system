package com.webest.coupon.application.client.dtos;

public record UserResponse(
    String userId,
    String userName,
    String email,
    Long addressCode
) {

}
