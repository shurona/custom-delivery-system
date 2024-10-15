package com.webest.auth.domain.model.vo;

import com.webest.auth.domain.model.TokenStatus;

public record RefreshTokenDto(
        String userId,
        String token,
        TokenStatus status
) {
}