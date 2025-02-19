package com.webest.user.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.data.redis")
public record RedisProperty(
    String host,
    String username,
    int port,
    String password
) {

}
