package com.webest.auth.presentation.dto.request;

public record RefreshRequest(
        String userId,
        String refreshToken
) {
}
