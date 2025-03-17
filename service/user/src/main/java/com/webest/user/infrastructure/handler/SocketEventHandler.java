package com.webest.user.infrastructure.handler;

import com.webest.app.security.JwtUtils;
import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketEventHandler {

    private final JwtUtils jwtUtils;

    // TODO: 세션을 외부 스토리지로 이전한다.
    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    public String getUserIdBySession(String session) {

        return sessions.getOrDefault(session, null);
    }


    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String accessToken = accessor.getFirstNativeHeader("Authorization");

        if (accessToken != null && accessToken.startsWith("Bearer ")) {

            String token = accessToken.substring(7);

            // 토큰 검증
            if (!jwtUtils.validateToken(token)) {
                throw new IllegalArgumentException("잘못된 JWT 토큰입니다.");
            }

            // 여기서 추출이 안되면 오류 발생될 것이라 기대한다.
            Claims claims = jwtUtils.extractClaims(token);

            // 세션 추가
            sessions.put(accessor.getSessionId(), claims.get("userId").toString());

            log.info("[세션 추가] {} [유저 아이디] {}",
                accessor.getSessionId(), claims.get("userId").toString());
        }
    }

    // 이벤트가 마무리 할 때 session을 지운다.
    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event) {
        sessions.remove(event.getSessionId());
    }

}
