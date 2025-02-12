package com.webest.store.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.webest.store.store.presentation.dto.StoreResponse;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class RedisCacheConfig {

    @Value("${redis.sentinel.name}")
    private String sentinelName;

    @Value("${redis.sentinel.one.host}")
    private String oneHost;

    @Value("${redis.sentinel.one.port}")
    private int onePort;

    @Value("${redis.sentinel.two.host}")
    private String twoHost;

    @Value("${redis.sentinel.two.port}")
    private int twoPort;

    @Value("${redis.sentinel.three.host}")
    private String threeHost;

    @Value("${redis.sentinel.three.port}")
    private int threePort;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.username}")
    private String username;

    @Value("${spring.data.redis.password}")
    private String password;

    @Profile("!test")
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisSentinelConfiguration conf = new RedisSentinelConfiguration()
            .master(sentinelName)
            .sentinel(oneHost, onePort)
            .sentinel(twoHost, twoPort)
            .sentinel(threeHost, threePort);

        conf.setPassword(RedisPassword.of(password));

        return new LettuceConnectionFactory(conf);
    }

    @Profile("test")
    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory redisTestConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(host);
        config.setUsername(username);
        config.setPort(port);
        config.setPassword(password);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisCacheManager defaultCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration
            .defaultCacheConfig()
            // null을 캐싱할지
            .disableCachingNullValues()
            // 기본 캐싱 유지 시간(Time to live)
            .entryTtl(Duration.ofMinutes(60))
            // 캐시를 구분하는 접두사 설정
            .computePrefixWith(CacheKeyPrefix.simple())
            // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
            .serializeValuesWith(
                SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(configuration).build();
    }

    @Bean
    public GeoOperations<String, String> geoOperations() {
        return geoRedisTemplate().opsForGeo();
    }

    // store 캐싱용
    @Bean(name = "storeRedisTemplate")
    public RedisTemplate<String, StoreResponse> storeRedisTemplate() {
        // ObjectMapper 생성 및 설정
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // JavaTimeModule 등록

        // Jackson2JsonRedisSerializer 사용
        Jackson2JsonRedisSerializer<StoreResponse> serializer = new Jackson2JsonRedisSerializer<>(
            objectMapper, StoreResponse.class);
        RedisTemplate<String, StoreResponse> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
    }

    // geo
    @Bean(name = "geoRedisTemplate")
    public RedisTemplate<String, String> geoRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
