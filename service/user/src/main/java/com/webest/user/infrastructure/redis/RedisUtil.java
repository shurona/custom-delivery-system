package com.webest.user.infrastructure.redis;

import com.webest.user.domain.model.vo.ShoppingCartDto;
import com.webest.user.presentation.dto.request.RefreshTokenDto;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisUtil {

    private static final String KEY_NAME = "CART:";
    private static final String TOKEN_PREFIX = "refresh_token:";
    private final RedisTemplate<String, ShoppingCartDto> shoppingCartDtoRedisTemplate;
    private final RedisTemplate<String, RefreshTokenDto> refreshTokenDtoRedisTemplate;

    // RefreshToken 전용
    public void setDataShoppingCart(
        ShoppingCartDto dto) {//key - 유저 데이터,value - refreshToken 지정된 시간(duration) 후에 데이터가 만료되도록 설정하는 메서드
        String key = KEY_NAME + dto.userId();
        // 만료시간 1일
        shoppingCartDtoRedisTemplate.opsForValue().set(key, dto, 1, TimeUnit.DAYS);
    }

    // RefreshToken 전용
    public ShoppingCartDto getShoppingCart(String userId) {
        String key = KEY_NAME + userId;
        return (ShoppingCartDto) shoppingCartDtoRedisTemplate.opsForValue().get(key);
    }

    public RefreshTokenDto getRefreshToken(String userId) {
        String key = TOKEN_PREFIX + userId;
        System.out.println("??? : " + key);
        return (RefreshTokenDto) refreshTokenDtoRedisTemplate.opsForValue().get(key);
    }
}