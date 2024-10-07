package com.webest.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Value("${token.secret-key}")
    private String secretKey;

    public AuthorizationHeaderFilter(){
        super(Config.class);
    }
    public static class Config{

    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
//            if (path.equals("/api/auth/signIn") || path.equals("/auth/signUp")) {
//                return chain.filter(exchange);  // /signIn,signUp 경로는 필터를 적용하지 않음
//            }

            ServerHttpRequest request = exchange.getRequest();

            // Header에 AUTHORIZATION에 관련된 값이 있는지 확인
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }

            String token = extractToken(exchange);

            if(!validateToken(token,exchange)){
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    // 토큰 분리
    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    // 토큰 검증
    // ServerWebExchange : 현재 HTTP 요청/응답 교환을 나타내는 객체로 요청 헤더를 수정할 수 있음
    private boolean validateToken(String token, ServerWebExchange exchange) {
        try {
            byte[] bytes = Base64.getDecoder().decode(secretKey);
            var secretKeyBytes = Keys.hmacShaKeyFor(bytes);
//            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));


            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKeyBytes)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            log.info("#####payload :: " + claims.toString());


            // 요청 헤더 수정 (Userid,role 추가)
            exchange.getRequest().mutate()
                    .header("X-UserId", claims.get("userId").toString())
                    .header("X-Role", claims.get("role").toString())
                    .build();
            // 추가적인 검증 로직 (예: 토큰 만료 여부 확인 등)을 여기에 추가할 수 있습니다.
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Mono, Flux -> Spring WebFlux
    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(message);
        return response.setComplete();
    }
}
