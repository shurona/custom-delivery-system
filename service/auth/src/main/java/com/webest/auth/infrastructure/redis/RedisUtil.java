package com.webest.auth.infrastructure.redis;

import com.webest.auth.domain.model.vo.RefreshTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String TOKEN_PREFIX = "refresh_token:";

    public Object getData(String key){//지정된 키(key)에 해당하는 데이터를 Redis에서 가져오는 메서드
        redisTemplate.opsForValue().get(key);
        return redisTemplate.opsForValue().get(key);
    }
    public void setData(String key,String value){//지정된 키(key)에 값을 저장하는 메서드
        redisTemplate.opsForValue().set(key,value);
    }
    // email 전용
    public void setDataExpire(String key,String value,long duration){//지정된 키(key)에 값을 저장하고, 지정된 시간(duration) 후에 데이터가 만료되도록 설정하는 메서드
        Duration expireDuration=Duration.ofSeconds(duration);
        redisTemplate.opsForValue().set(key,value,expireDuration);
    }

    // RefreshToken 전용
    public void setDataRefreshToken(RefreshTokenDto dto){//key - 유저 데이터,value - refreshToken 지정된 시간(duration) 후에 데이터가 만료되도록 설정하는 메서드
        String key = TOKEN_PREFIX + dto.userId();
        // 만료시간 1시간
        redisTemplate.opsForValue().set(key,dto,1, TimeUnit.HOURS);
    }

    // RefreshToken 전용
    public RefreshTokenDto getRefreshToken(String userId) {
        String key = TOKEN_PREFIX + userId;
        return (RefreshTokenDto) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key){//지정된 키(key)에 해당하는 데이터를 Redis에서 삭제하는 메서드
        redisTemplate.delete(key);
    }
}