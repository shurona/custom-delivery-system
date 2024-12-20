package com.webest.auth.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webest.auth.common.exception.AuthErrorCode;
import com.webest.auth.common.exception.AuthException;
import com.webest.auth.domain.model.Auth;
import com.webest.auth.domain.model.vo.AuthDto;
import com.webest.auth.domain.model.vo.RefreshTokenDto;
import com.webest.auth.domain.repository.AuthRepository;
import com.webest.auth.infrastructure.core.RiderClient;
import com.webest.auth.infrastructure.core.UserClient;
import com.webest.auth.infrastructure.redis.RedisUtil;
import com.webest.auth.presentation.dto.request.RefreshRequest;
import com.webest.auth.presentation.dto.request.UserJoinRequest;
import com.webest.web.common.UserRole;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AuthServiceImplTest {

    private final String SECRET_KEY = "dGVzdE1vY2t0ZXN0TW9ja3Rlc3RNb2NrdGVzdE1vY2t0ZXN0TW9ja3Rlc3RNb2Nr";
    private final String TOKEN_TIME = "36000000";

    @Autowired
    private AuthRepository authRepository;
    @Mock
    private UserClient userClient;
    @Mock
    private RiderClient riderClient;
    @Mock
    private RedisUtil redisUtil;

    @Spy
    private JwtTokenService jwtTokenService = new JwtTokenService(SECRET_KEY, TOKEN_TIME);
    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthServiceImpl(authRepository, userClient, riderClient, redisUtil,
            jwtTokenService, SECRET_KEY, TOKEN_TIME);
    }

    @Nested
    @DisplayName("유저 간단 저장 조회 테스트")
    public class AuthSimpleTest {

        @Test
        public void 기본_저장확인_테스트() {
            // given
            String userId = "userId";
            String password = "password";
            String userName = "userName";
            String email = "abc1234@abcde.abc";
            String phone = "010-2341-2020";
            UserRole role = UserRole.USER;
            String city = "city";
            String street = "street";
            String district = "district";
            String detailAddress = "detail";

            UserJoinRequest userJoinRequest = new UserJoinRequest(
                userId, password, userName, email, phone, role, city, street, district,
                detailAddress
            );
            // when
            Auth pwd = authRepository.save(
                Auth.from(AuthDto.from(userJoinRequest, "pwd", 3377927373L)));

            Auth auth = authRepository.findById(pwd.getId()).orElseThrow();

            // then
            assertThat(userId).isEqualTo(auth.getUserId());
        }

        @Test
        public void 없는_유저_조회_테스트() {
            // given
            String username = "username";

            // when
            Throwable throwable = catchThrowable(() -> {
                authService.loadUserByUsername(username);
            });

            Throwable throwTwo = catchThrowable(() -> {
                authService.getUserDetailsByUserId(username);
            });

            // then
            // 첫 번째
            assertThat(throwable)
                .isInstanceOf(AuthException.class)
                .hasMessage(AuthErrorCode.USER_NOT_FOUND.getMessage());

            // 두 번째
            assertThat(throwTwo)
                .isInstanceOf(AuthException.class)
                .hasMessage(AuthErrorCode.USER_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("리프레시 토큰")
    public class RefreshToken {

        @BeforeEach
        public void dbSetUp() {
            String userId = "userId";
            String password = "password";
            String userName = "userName";
            String email = "abc1234@abcde.abc";
            String phone = "010-2341-2020";
            UserRole role = UserRole.USER;
            String city = "city";
            String street = "street";
            String district = "district";
            String detailAddress = "detail";

            UserJoinRequest userJoinRequest = new UserJoinRequest(
                userId, password, userName, email, phone, role, city, street, district,
                detailAddress
            );

            Auth pwd = authRepository.save(
                Auth.from(AuthDto.from(userJoinRequest, "pwd", 3377927373L)));
        }

        @Test
        public void 성공_플로우() {
            // given
            String userId = "userId";
            String refreshToken = "refreshToken";
            RefreshRequest refreshRequest = new RefreshRequest(userId, refreshToken);

            RefreshTokenDto refreshTokenDto = mock(RefreshTokenDto.class);
            when(refreshTokenDto.token()).thenReturn(refreshToken);
            when(redisUtil.getRefreshToken(userId)).thenReturn(refreshTokenDto);

            // when
            String refreshedToken = authService.refreshToken(refreshRequest);

            Claims claims = jwtTokenService.extractClaims(refreshedToken);
            // then
            assertThat(claims.getSubject()).isEqualTo(userId);

        }

        @Test
        public void 실패_플로우() {
            // given
            String userId = "userId";
            String refreshToken = "refreshToken";
            RefreshRequest refreshRequest = new RefreshRequest(userId, refreshToken);

            RefreshTokenDto refreshTokenDto = mock(RefreshTokenDto.class);
            when(refreshTokenDto.token()).thenReturn(refreshToken + "not");
            when(redisUtil.getRefreshToken(userId)).thenReturn(refreshTokenDto);

            // when
            Throwable invalidRefreshThrow = catchThrowable(() -> {
                authService.refreshToken(refreshRequest);
            });

            // then
            assertThat(invalidRefreshThrow)
                .isInstanceOf(AuthException.class)
                .hasMessage(AuthErrorCode.INVALID_REFRESH_TOKEN.getMessage());

        }

    }

}