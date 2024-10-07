package com.webest.auth.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record JoinResponse(
        String userId,
        String userName,
        String email,
        Integer addressCode
) {
}
