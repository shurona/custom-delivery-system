package com.webest.store.store.application;

import com.webest.store.store.presentation.dto.StoreResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class StoreCacheService {

    private final RedisTemplate<String, StoreResponse> storeRedisTemplate;

    private static final String STORE_CACHE_PREFIX = "store:";
    private static final Duration CACHE_EXPIRATION = Duration.ofMinutes(30); // 캐시 만료 시간 설정


    public StoreCacheService(@Qualifier("storeRedisTemplate") RedisTemplate<String, StoreResponse> storeRedisTemplate) {
        this.storeRedisTemplate = storeRedisTemplate;
    }

    public void cacheStore(Long storeId, StoreResponse response) {
        String key = generateKey(storeId);
        storeRedisTemplate.opsForValue().set(key, response, CACHE_EXPIRATION);
    }

    public StoreResponse getCachedStore(Long storeId) {
        String key = generateKey(storeId);
        return storeRedisTemplate.opsForValue().get(key);
    }

    public void deleteStoreCache(Long storeId) {
        String key = generateKey(storeId);
        storeRedisTemplate.delete(key);
    }

    private String generateKey(Long storeId) {
        return STORE_CACHE_PREFIX + storeId;
    }

}
