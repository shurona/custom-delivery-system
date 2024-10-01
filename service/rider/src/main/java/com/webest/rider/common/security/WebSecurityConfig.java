package com.webest.rider.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(
            (auth) ->
                auth
                    .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations())
                    .permitAll() // resources 접근 허용 설정
                    .requestMatchers("/api/v1/riders/**")
                    .permitAll() // Rider Rest api 접근 허용 설정
                    .requestMatchers("/internal/**")
                    .permitAll() // Rider Endpoint 접근 허용
                    .anyRequest()
                    .permitAll() // 현재는 열어두지만 추후 막아둬야 함
        );

        return http.build();
    }
}
