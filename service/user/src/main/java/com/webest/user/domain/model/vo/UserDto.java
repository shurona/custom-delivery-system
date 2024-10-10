package com.webest.user.domain.model.vo;

import com.webest.user.presentation.dto.request.UserJoinRequest;
import com.webest.user.presentation.dto.response.UserResponse;
import com.webest.web.common.UserRole;

public record UserDto(
        String userId,
        String password,
        String userName,
        String email,
        String phone,
        UserRole role,       // 권한 체크
        Long addressCode,
        String city,
        String street,
        String district
) {


    // AuthDto -> JoinResponse
    public UserResponse to() {
        return new UserResponse(
                this.userId,
                this.userName,
                this.email,
                this.addressCode
        );
    }

    // JoinRequest -> AuthDto
    public static UserDto from(UserJoinRequest request, String pw, Long code) {
        return new UserDto(
                request.userId(),
                pw,
                request.userName(),
                request.email(),
                request.phone(),
                request.role(),
                code,
                request.city(),
                request.street(),
                request.district()
        );
    }

}
