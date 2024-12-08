package com.webest.auth.application;

import static com.webest.auth.common.exception.AuthErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.webest.auth.common.exception.AuthErrorCode;
import com.webest.auth.common.exception.AuthException;
import com.webest.auth.domain.model.RiderTransportation;
import com.webest.auth.domain.model.vo.RefreshTokenDto;
import com.webest.auth.infrastructure.core.RiderClient;
import com.webest.auth.infrastructure.core.dto.RiderResponseDto;
import com.webest.auth.infrastructure.redis.RedisUtil;
import com.webest.auth.presentation.dto.request.rider.RiderAuthRequestDto;
import com.webest.web.response.CommonResponse;
import feign.FeignException;
import feign.Request;
import feign.Request.HttpMethod;
import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RiderAuthServiceTest {

    @InjectMocks
    private RiderAuthService riderAuthService;

    @Mock
    private RiderClient riderClient;

    @Mock
    private RedisUtil redisUtil;

    @Spy
    private JwtTokenService jwtTokenService = new JwtTokenService(
        "dGVzdE1vY2t0ZXN0TW9ja3Rlc3RNb2NrdGVzdE1vY2t0ZXN0TW9ja3Rlc3RNb2Nr", "3600");

    @BeforeEach
    void setUp() {
        // `@Value`로 주입되는 값은 생성자로 전달
        riderAuthService = new RiderAuthService(riderClient, redisUtil, jwtTokenService, "3600");

    }

    @DisplayName("라이더 인증 서비스 성공")
    @Test
    public void 인증_성공_테스트() {

        String userId = "userId";
        RiderAuthRequestDto requestDto = new RiderAuthRequestDto(userId, "password");
        RefreshTokenDto refreshTokenDtoMock = mock(RefreshTokenDto.class);

        // given
        RiderResponseDto givenRider = new RiderResponseDto(
            1L, userId, Arrays.asList(1L, 2L), RiderTransportation.MOTORCYCLE,
            LocalDateTime.now());

        doReturn(new CommonResponse<RiderResponseDto>(200, "메시지", givenRider))
            .when(riderClient).authRider(requestDto);

//        doNothing().when(redisUtil).setDataRefreshToken(refreshTokenDtoMock);
//        redisUtil.setDataRefreshToken(refreshTokenDtoMock);
        doNothing().when(redisUtil).setDataRefreshToken(any(RefreshTokenDto.class));

        // when
        String token = riderAuthService.riderAuth(requestDto);
        Claims claims = jwtTokenService.extractClaims(token);

        // then
        // token의 subject 확인
        assertThat(claims.getSubject()).isEqualTo(userId);

        // 내부 함수 호출 횟수 확인
        verify(redisUtil, times(1)).setDataRefreshToken(any(RefreshTokenDto.class));

    }

    @Nested
    @DisplayName("라이더 모듈에서 에러발생 경우")
    class errorInRiderModule {

        String userId;
        RiderAuthRequestDto requestDto;
        Request request;

        @BeforeEach
        public void riderModuleSetUp() {
            userId = "userId";
            requestDto = new RiderAuthRequestDto(userId, "password");
            // rider Request
            request = Request.create(
                HttpMethod.GET,
                "/api/v1/riders/auth",
                Map.of(), // headers
                null, // body
                null, // charset
                null // Request Template
            );
        }

        @DisplayName("유저가 없는 경우")
        @Test
        public void 유저가_없는_에러() {
            //given
            // 입력은 beforeEach에서 생성

            // when

            doThrow(new FeignException.NotFound("Rider not found", request, null, null)).when(
                riderClient).authRider(requestDto);

            assertThatExceptionOfType(AuthException.class).isThrownBy(() -> {
                String token = riderAuthService.riderAuth(requestDto);
            }).withMessage(USER_NOT_FOUND.getMessage());

            // then
            assertThatThrownBy(() -> {
                String token = riderAuthService.riderAuth(requestDto);
            })
                .isInstanceOf(AuthException.class)
                .hasMessage(USER_NOT_FOUND.getMessage());
        }

        @DisplayName("비밀번호가 다름")
        @Test
        public void 비밀번호_미일치() {
            //given
            // 입력은 beforeEach에서 생성

            // when
            doThrow(new FeignException.BadRequest("Invalid Password", request, null, null)).when(
                riderClient).authRider(requestDto);

            Throwable error = Assertions.catchThrowable(() -> {
                String token = riderAuthService.riderAuth(requestDto);
            });

            // then
            Assertions.assertThat(error)
                .isInstanceOf(AuthException.class)
                .hasMessage(AuthErrorCode.INVALID_PASSWORD.getMessage());

        }
    }


}