package com.webest.auth.application;

import com.webest.web.common.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

//@RequiredArgsConstructor
@Service
public class JwtTokenService {

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private final String secretKey;

    private final String tokenTime;

    public JwtTokenService(
        @Value("${token.secret-key}") String secretKey,
        @Value("${token.expiration-time}") String tokenTime) {
        this.secretKey = secretKey;
        this.tokenTime = tokenTime;
    }

    public String createToken(String userId, UserRole role, Long tokenTime) {

        byte[] bytes = Base64.getDecoder().decode(secretKey);
        Key key = Keys.hmacShaKeyFor(bytes);

        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("userId", userId);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenTime);

        // 엑세스 토큰
        String token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, signatureAlgorithm)
            .compact();

        return token;
    }

    // 토큰 분리
    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    // 토큰 암호화 제거
    public Claims extractClaims(String token) {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        var secretKeyBytes = Keys.hmacShaKeyFor(bytes);
//            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));

        Jws<Claims> claimsJws = Jwts.parserBuilder()
            .setSigningKey(secretKeyBytes)
            .build()
            .parseClaimsJws(token);

        return claimsJws.getBody();
    }
}
