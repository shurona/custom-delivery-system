package com.webest.store.store.infra.naver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverGeoClientHeaderConfig {
    @Bean
    public NaverRequestInterceptor requestInterceptor() {
        return new NaverRequestInterceptor();
    }
}
