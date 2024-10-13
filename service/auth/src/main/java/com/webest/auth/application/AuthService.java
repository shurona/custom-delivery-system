package com.webest.auth.application;

import com.webest.auth.domain.model.vo.AuthDto;
import com.webest.auth.presentation.dto.request.RefreshRequest;
import com.webest.auth.presentation.dto.request.RiderCreateRequestDto;
import com.webest.auth.presentation.dto.request.UserJoinRequest;
import com.webest.auth.presentation.dto.response.JoinResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    public JoinResponse create(UserJoinRequest userJoinRequest);

    AuthDto getUserDetailsByUserId(String userName);

    Long createRider(RiderCreateRequestDto requestDto);

    String refreshToken(RefreshRequest request);

    void logout(String userId);
}
