package com.webest.auth.infrastructure.redis;

import com.webest.auth.domain.model.vo.RefreshTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final RedisTemplate<String, RefreshTokenDto> refreshTokenRedisTemplate;
    private final RedisTemplate<String, String> stringRedisTemplate;

    private static final String TOKEN_PREFIX = "refresh_token:";

    public RedisUtil(@Qualifier("customStringRedisTemplate") RedisTemplate<String, String> stringRedisTemplate,
                     @Qualifier("customRefreshTokenRedisTemplate") RedisTemplate<String, RefreshTokenDto> refreshTokenRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.refreshTokenRedisTemplate = refreshTokenRedisTemplate;
    }

    public Object getData(String key){//지정된 키(key)에 해당하는 데이터를 Redis에서 가져오는 메서드
        stringRedisTemplate.opsForValue().get(key);
        return stringRedisTemplate.opsForValue().get(key);
    }

    // email 전용
    public void setDataExpire(String key,String value,long duration){//지정된 키(key)에 값을 저장하고, 지정된 시간(duration) 후에 데이터가 만료되도록 설정하는 메서드
        Duration expireDuration=Duration.ofSeconds(duration);
        stringRedisTemplate.opsForValue().set(key,value,expireDuration);
    }

    // RefreshToken 전용
    public void setDataRefreshToken(RefreshTokenDto dto){//key - 유저 데이터,value - refreshToken 지정된 시간(duration) 후에 데이터가 만료되도록 설정하는 메서드
        String key = TOKEN_PREFIX + dto.userId();
        // 만료시간 1시간
        refreshTokenRedisTemplate.opsForValue().set(key,dto,1, TimeUnit.HOURS);
    }

    // RefreshToken 전용
    public RefreshTokenDto getRefreshToken(String userId) {
        String key = TOKEN_PREFIX + userId;
        return (RefreshTokenDto) refreshTokenRedisTemplate.opsForValue().get(key);
    }
}