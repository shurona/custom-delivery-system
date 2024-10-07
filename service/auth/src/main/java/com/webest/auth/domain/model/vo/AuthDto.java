package com.webest.auth.domain.model.vo;

import com.webest.auth.presentation.dto.request.JoinRequest;
import com.webest.auth.presentation.dto.response.JoinResponse;
import com.webest.web.common.UserRole;

public record AuthDto(
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
    // JoinRequest -> AuthDto
    public static AuthDto from(JoinRequest request,String pw, Integer code) {
        return new AuthDto(
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


    // AuthDto -> JoinResponse
    public JoinResponse to() {
        return new JoinResponse(
                this.userId,
                this.userName,
                this.email,
                this.addressCode
        );
    }
}
