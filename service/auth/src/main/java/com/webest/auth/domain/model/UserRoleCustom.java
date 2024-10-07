package com.webest.auth.domain.model;

import com.webest.web.common.UserRole;
import org.springframework.security.core.GrantedAuthority;

public class UserRoleCustom implements GrantedAuthority {
    private String role;

    public UserRoleCustom(UserRole role) {
        this.role = String.valueOf(role);
    }

    @Override
    public String getAuthority() {
        return role; // 권한 이름 반환
    }

}
