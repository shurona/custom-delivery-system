package com.webest.auth.application;

import com.webest.auth.domain.model.vo.AuthDto;
import com.webest.auth.presentation.dto.request.JoinRequest;
import com.webest.auth.presentation.dto.response.JoinResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    public JoinResponse create(JoinRequest joinRequest);

    AuthDto getUserDetailsByUserId(String userName);
}
