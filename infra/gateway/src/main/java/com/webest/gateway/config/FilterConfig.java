package com.webest.gateway.config;

import com.webest.gateway.filter.AuthorizationHeaderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final AuthorizationHeaderFilter authorizationHeaderFilter;


    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

            .route("auth-login-route", r -> r.path("/api/auth/login")
                .and()
                .method("POST")
                .filters(f -> f
                    .removeRequestHeader("Cookie")
                    .rewritePath("/api/auth/(?<segment>.*)", "/${segment}"))
                .uri("lb://AUTH-SERVICE"))

            .route("auth-login-route", r -> r.path("/api/auth/**")
                .and()
                .method("POST", "GET")
                .filters(f -> f
                    .removeRequestHeader("Cookie"))
                .uri("lb://AUTH-SERVICE"))

            .route("user-get-all-route", r -> r.path("/api/users/**")
                .and()
                .method("GET", "POST", "PATCH", "DELETE")
                .filters(f -> f
                    .removeRequestHeader("Cookie")
                    .filter(authorizationHeaderFilter.apply(
                        new AuthorizationHeaderFilter.Config())))  // 필터 팩토리로 필터 생성
                .uri("lb://USER-SERVICE"))

            .route("user-get-all-route", r -> r.path("/api/v1/orders/**")
                .and()
                .method("GET", "POST", "PATCH", "DELETE")
                .filters(f -> f
                    .removeRequestHeader("Cookie")
                    .filter(authorizationHeaderFilter.apply(
                        new AuthorizationHeaderFilter.Config())))  // 필터 팩토리로 필터 생성
                .uri("lb://ORDER-SERVICE"))

            .route("user-get-all-route", r -> r.path("/api/v1/deliveries/**")
                .and()
                .method("GET", "POST", "PATCH", "DELETE")
                .filters(f -> f
                    .removeRequestHeader("Cookie")
                    .filter(authorizationHeaderFilter.apply(
                        new AuthorizationHeaderFilter.Config())))  // 필터 팩토리로 필터 생성
                .uri("lb://DELIVERY-SERVICE"))

            .route("user-get-all-route", r -> r.path("/api/v1/stores/**")
                .and()
                .method("GET", "POST", "PATCH", "DELETE", "PUT")
                .filters(f -> f
                    .removeRequestHeader("Cookie")
                    .filter(authorizationHeaderFilter.apply(
                        new AuthorizationHeaderFilter.Config())))  // 필터 팩토리로 필터 생성
                .uri("lb://STORE-SERVICE"))

            .route("user-get-all-route", r -> r.path("/api/v1/categories/**")
                .and()
                .method("GET", "POST", "PATCH", "DELETE", "PUT")
                .filters(f -> f
                    .removeRequestHeader("Cookie")
                    .filter(authorizationHeaderFilter.apply(
                        new AuthorizationHeaderFilter.Config())))  // 필터 팩토리로 필터 생성
                .uri("lb://STORE-SERVICE"))

            .route("user-get-all-route", r -> r.path("/api/v1/products/**")
                .and()
                .method("GET", "POST", "PATCH", "DELETE", "PUT")
                .filters(f -> f
                    .removeRequestHeader("Cookie")
                    .filter(authorizationHeaderFilter.apply(
                        new AuthorizationHeaderFilter.Config())))  // 필터 팩토리로 필터 생성
                .uri("lb://PRODUCT-SERVICE"))

            .route("user-get-all-route", r -> r.path("/api/v1/coupons/**")
                .and()
                .method("GET", "POST", "PATCH", "DELETE")
                .filters(f -> f
                    .removeRequestHeader("Cookie")
                    .filter(authorizationHeaderFilter.apply(
                        new AuthorizationHeaderFilter.Config())))  // 필터 팩토리로 필터 생성
                .uri("lb://COUPON-SERVICE"))

            .route("user-get-all-route", r -> r.path("/api/v1/riders/**")
                .and()
                .method("GET", "POST", "PATCH", "PUT", "DELETE")
                .filters(f -> f
                    .removeRequestHeader("Cookie")
                    .filter(authorizationHeaderFilter.apply(
                        new AuthorizationHeaderFilter.Config())))  // 필터 팩토리로 필터 생성
                .uri("lb://RIDER-SERVICE"))
            .build();


    }
}
