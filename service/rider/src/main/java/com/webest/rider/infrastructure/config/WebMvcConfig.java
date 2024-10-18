package com.webest.rider.infrastructure.config;

import com.webest.rider.presentation.interceptor.RiderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RiderInterceptor())
            .excludePathPatterns("/api/v1/riders/city/**");
    }
}
