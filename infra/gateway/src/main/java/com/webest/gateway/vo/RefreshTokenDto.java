package com.webest.gateway.vo;

public record RefreshTokenDto(
        String userId,
        String token,
        TokenStatus status
) {
}