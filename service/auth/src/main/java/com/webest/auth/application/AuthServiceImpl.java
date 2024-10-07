package com.webest.auth.application;

import com.webest.auth.common.exception.AuthErrorCode;
import com.webest.auth.common.exception.AuthException;
import com.webest.auth.domain.model.Auth;
import com.webest.auth.domain.model.UserRoleCustom;
import com.webest.auth.domain.model.vo.AuthDto;
import com.webest.auth.domain.repository.AuthRepository;
import com.webest.auth.presentation.dto.request.JoinRequest;
import com.webest.auth.presentation.dto.response.JoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public JoinResponse create(JoinRequest joinRequest) {
        // TODO :: 시/구/동 주소를 통해 주소 코드 찾아내는 코드 추가할 예정
        AuthDto dto = AuthDto.from(joinRequest,passwordEncoder.encode(joinRequest.password()),null);
        authRepository.save(Auth.from(dto));
        return dto.to();
    }


    @Override
    public AuthDto getUserDetailsByUserId(String userName) {
        Auth auth = authRepository.findByUserId(userName)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));
        return auth.to();
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Auth auth = authRepository.findByUserId(userName)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        // UserRole을 List<GrantedAuthority>로 변환
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new UserRoleCustom(auth.getRole())); // UserRole을 GrantedAuthority로 추가

        return new User(auth.getUserId(), auth.getPassword(), true,true,true,true,authorities);    // new ArrayList -> 권한 추가

    }
}
