package com.webest.auth.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.webest.auth.domain.model.RiderTransportation;
import com.webest.auth.domain.model.vo.RefreshTokenDto;
import com.webest.auth.infrastructure.core.RiderClient;
import com.webest.auth.infrastructure.core.dto.RiderResponseDto;
import com.webest.auth.infrastructure.redis.RedisUtil;
import com.webest.auth.presentation.dto.request.rider.RiderAuthRequestDto;
import com.webest.web.response.CommonResponse;
import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        Assertions.assertThat(claims.getSubject()).isEqualTo(userId);

        // 내부 함수 호출 횟수 확인
        verify(redisUtil, times(1)).setDataRefreshToken(any(RefreshTokenDto.class));

    }

}