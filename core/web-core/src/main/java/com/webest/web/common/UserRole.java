package com.webest.web.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    RIDER(Authority.RIDER),
    USER(Authority.USER),
    OWNER(Authority.OWNER),
    MASTER(Authority.MASTER);

    private final String authority;

    public static class Authority {

        public static final String RIDER = "ROLE_RIDER";
        public static final String USER = "ROLE_USER";
        public static final String OWNER = "ROLE_OWNER";
        public static final String MASTER = "ROLE_MASTER";
    }

}
