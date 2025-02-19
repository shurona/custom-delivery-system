package com.webest.user.presentation.interceptor;

import com.webest.user.common.JwtUtils;
import com.webest.user.common.TokenStatus;
import com.webest.user.infrastructure.redis.RedisUtil;
import com.webest.user.presentation.dto.request.RefreshTokenDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserChatRoomJwtCheck implements ChannelInterceptor {

    private final JwtUtils jwtUtils;
    private final String JWT_PREFIX = "Bearer ";
    private final RedisUtil redisUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // 접속할 때에만 token 검증
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith(JWT_PREFIX)) {
                throw new IllegalArgumentException("JWT 토큰이 들어있지 않아요");
            }

            // "Bearer " 제거
            String token = authHeader.substring(7);

            // 토큰 검증
            if (!jwtUtils.validateToken(token)) {
                throw new IllegalArgumentException("잘못된 JWT 토큰입니다.");
            }

            Claims claims = jwtUtils.extractClaims(token);
            
            RefreshTokenDto dto;
            try {
                dto = redisUtil.getRefreshToken(claims.get("userId").toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Redis에서 값을 갖고 올 때 문제 발생");
            }

            if (dto == null || dto.status() == TokenStatus.DEACTIVATE) {
                throw new IllegalArgumentException("만료된 토큰입니다.");
            }

        }

        return message;
    }
}
