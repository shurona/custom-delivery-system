//package com.webest.store.store.redis;
//
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.CacheKeyPrefix;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//import java.time.Duration;
//
//@Configuration
//public class CacheConfig {
//    @Bean
//    RedisCacheManager cacheManager(
//            RedisConnectionFactory redisConnectionFactory
//    ) {
//        // Redis를 이용해서 Spring Cache를 사용할 때 Redis 관련 설정을 모아두는 클래스
//        RedisCacheConfiguration configuration = RedisCacheConfiguration
//                .defaultCacheConfig()
//                // null 캐싱 x
//                .disableCachingNullValues()
//                // 기본 캐시 유지 시간 (Time To Live)
//                .entryTtl(Duration.ofSeconds(60))
//                // 캐시를 구분하는 접두사 설정
//                .computePrefixWith(CacheKeyPrefix.simple())
//                // 캐시에 저장할 Value 를 어떻게 직렬화/역직렬화 할 것인지
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java())
//                );
//        return RedisCacheManager
//                .builder(redisConnectionFactory)
//                .cacheDefaults(configuration)
//                .build();
//    }
//}