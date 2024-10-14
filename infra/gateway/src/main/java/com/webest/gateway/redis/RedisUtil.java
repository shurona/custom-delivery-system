package com.webest.gateway.redis;

import com.webest.gateway.vo.RefreshTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String TOKEN_PREFIX = "refresh_token:";

    // RefreshToken 전용
    public RefreshTokenDto getRefreshToken(String userId) {
        String key = TOKEN_PREFIX + userId;
        return (RefreshTokenDto) redisTemplate.opsForValue().get(key);
    }
    
}