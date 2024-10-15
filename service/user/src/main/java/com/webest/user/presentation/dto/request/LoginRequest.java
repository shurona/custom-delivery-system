package com.webest.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginRequest(
        @NotNull(message = "UserId cannot be null")
        String userId,

        @NotNull(message = "Password cannot be null")
        @Size(min = 8, message = "Password must be equals or grater than 8 characters")
        String password
) {
}
