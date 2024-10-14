package com.webest.order.presentation.response;

public record UserResponse(Long id,
                           String username,
                           String number,
                           String email,
                           Long addressCode) {

}
