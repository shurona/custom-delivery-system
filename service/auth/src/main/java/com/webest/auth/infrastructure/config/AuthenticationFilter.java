package com.webest.auth.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webest.auth.application.AuthService;
import com.webest.auth.domain.model.vo.AuthDto;
import com.webest.auth.presentation.dto.request.LoginRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String secretKey;

    private String tokenTime;

    private AuthService authService;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private Key key;

    public AuthenticationFilter(AuthenticationManager authenticationManager, AuthService authService,String secretKey,String tokenTime) {
        super(authenticationManager);
        this.authService = authService;
        this.secretKey = secretKey;
        this.tokenTime = tokenTime;
    }

    /*
        1. 유저가 현재 로그인한 데이터값을 LoginRequest값과 맵핑 시켜준다.
        2. 이후 getAuthenticationManager().authenticate를 통해 ServiceImpl에 loadUserByUsername를 통해 검증 진행
        3. 해당 검증이 정상이라면 successfulAuthentication를 통해 결과값 반환
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request
            , HttpServletResponse response) throws AuthenticationException {
        try{
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);     // 로그인 데이터를 LoginRequest 형태로 맵핑
            // 인증 정보
            // 유저에게 입력받은 email과 password를 UsernamePasswordAuthenticationToken를 입혀서 인증검사를 대신 해주는 getAuthenticationManager로 보내 검사 진행
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.userId(),
                            loginRequest.password(),
                            new ArrayList<>())
            );

        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }


    // 인증 성공했을때 반환 값
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);

        String userName = (((User)authResult.getPrincipal()).getUsername());
        AuthDto userDetails = authService.getUserDetailsByUserId(userName);

        Claims claims = Jwts.claims().setSubject(String.valueOf(userDetails.userId()));
        claims.put("userId", userDetails.userId());
        claims.put("role", userDetails.role());

        Date now = new Date();
        Date validity = new Date(now.getTime() + Long.parseLong(tokenTime));

        String token = Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(validity)
                        .signWith(key, signatureAlgorithm)
                        .compact();

        response.addHeader("token", "Bearer " + token);
    }
}
