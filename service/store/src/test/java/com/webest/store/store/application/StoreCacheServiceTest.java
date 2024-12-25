package com.webest.store.store.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webest.store.store.presentation.dto.StoreResponse;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
class StoreCacheServiceTest {

    @Mock
    private RedisTemplate<String, StoreResponse> storeRedisTemplate;

    @Mock
    private ValueOperations<String, StoreResponse> valueOperations;

    @InjectMocks
    private StoreCacheService storeCacheService;

    @DisplayName("저장 입력시 호출확인")
    @Test
    void testCacheStore() {
        // Given
        Long storeId = 1L;
        StoreResponse response = Mockito.mock(StoreResponse.class);
        String expectedKey = "store:" + storeId;

        when(storeRedisTemplate.opsForValue()).thenReturn(valueOperations);

        // When
        storeCacheService.cacheStore(storeId, response);

        // Then
        verify(valueOperations).set(eq(expectedKey), eq(response), eq(Duration.ofMinutes(30)));
    }

    @DisplayName("단순 조회 테스트")
    @Test
    void testGetCachedStore() {
        // Given
        Long storeId = 1L;
        StoreResponse response = Mockito.mock(StoreResponse.class);
        String expectedKey = "store:" + storeId;

        when(storeRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(expectedKey)).thenReturn(response);

        // When
        StoreResponse cachedResponse = storeCacheService.getCachedStore(storeId);

        // Then
        assertEquals(response, cachedResponse);
    }

    @Test
    void testDeleteStoreCache() {
        // Given
        Long storeId = 1L;
        String expectedKey = "store:" + storeId;

        // When
        storeCacheService.deleteStoreCache(storeId);

        // Then
        verify(storeRedisTemplate).delete(eq(expectedKey));
    }
}