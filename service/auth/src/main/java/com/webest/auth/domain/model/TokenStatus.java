package com.webest.auth.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenStatus {
    ACTIVE("활성화"),
    DEACTIVATE("비활성화");

    private final String type;
}
