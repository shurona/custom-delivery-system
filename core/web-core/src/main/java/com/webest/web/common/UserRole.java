package com.webest.web.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    RIDER(Authority.RIDER), // 라이더
    USER(Authority.USER),   // 유저
    OWNER(Authority.OWNER), // 가게 주인
    MASTER(Authority.MASTER); // 관리자

    private final String authority;

    public static class Authority {

        public static final String RIDER = "ROLE_RIDER";
        public static final String USER = "ROLE_USER";
        public static final String OWNER = "ROLE_OWNER";
        public static final String MASTER = "ROLE_MASTER";
    }

}
