package com.webest.user.presentation.dto.request;

import com.webest.user.common.TokenStatus;

public record RefreshTokenDto(
    String userId,
    String token,
    TokenStatus status
) {

}
