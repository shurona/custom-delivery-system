package com.webest.user.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenStatus {
    ACTIVE("활성화"),
    DEACTIVATE("비활성화");

    private final String type;
}
