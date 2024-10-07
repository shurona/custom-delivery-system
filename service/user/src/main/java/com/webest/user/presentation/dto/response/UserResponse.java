package com.webest.user.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        long id,
        String userId,
        String userName,
        String email,
        Integer addressCode
) {
}
