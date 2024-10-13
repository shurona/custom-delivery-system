package com.webest.gateway.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenStatus {
    ACTIVE("활성화"),
    DEACTIVATE("비활성화");

    private final String type;
}
