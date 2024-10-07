package com.webest.user.domain.model.vo;

import com.webest.user.presentation.dto.response.UserResponse;
import com.webest.web.common.UserRole;

public record UserDto(
        long id,
        String userId,
        String password,
        String userName,
        String email,
        String phone,
        UserRole role,       // 권한 체크
        Integer addressCode,
        String city,
        String street,
        String district
) {


    // AuthDto -> JoinResponse
    public UserResponse to() {
        return new UserResponse(
                this.id,
                this.userId,
                this.userName,
                this.email,
                this.addressCode
        );
    }
}
