package com.webest.user.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webest.app.address.csv.ReadAddressCsv;
import com.webest.app.address.service.AddressDto;
import com.webest.user.application.GoogleApiCall;
import com.webest.user.domain.model.User;
import com.webest.user.domain.model.vo.UserDto;
import com.webest.user.domain.repository.UserRepository;
import com.webest.user.infrastructure.redis.RedisUtil;
import com.webest.user.presentation.dto.request.UserJoinRequest;
import com.webest.user.presentation.dto.request.UserUpdateRequest;
import com.webest.user.presentation.dto.response.UserResponse;
import com.webest.web.common.UserRole;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReadAddressCsv readAddressCsv;

    @Mock
    private GoogleApiCall googleApiCall;

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private RedisUtil redisUtil;


    @DisplayName("유저 생성 테스트")
    @Test
    public void 유저_생성_및_조회_테스트() {
        // given
        String userId = "userId";
        String userName = "userName";
        String email = "abc1234@abcde.abc";
        String detailAddress = "상세 주소";
        UserJoinRequest request = new UserJoinRequest(userId, "password", userName,
            email, "010-2929-9192", UserRole.USER,
            "서울", "스트리트", "지역", "상세주소");

        double[] doubleEx = new double[]{38.239329, 39.239329};

        when(googleApiCall.getGeocode(any(String.class))).thenReturn(doubleEx);
//        doReturn(doubleEx).when(googleApiCall).getGeocode("temp");

        UserResponse response = new UserResponse(userId, userName, email, 1111011400L,
            detailAddress, doubleEx[0], doubleEx[1]);

        when(readAddressCsv.findAddressByDistrict(request.city(), request.street(),
            request.district())).thenReturn(
            new AddressDto(1111011400L,
                request.city(), request.street(), request.district()));

        when(userRepository.save(any(User.class))).thenReturn(null);
        // when
        UserResponse userResponse = userService.create(request);

        // then
        assertThat(userResponse.userId()).isEqualTo(userId);
        assertThat(new double[]{userResponse.latitude(), userResponse.longitude()})
            .containsExactly(doubleEx);
    }


    @Nested
    @DisplayName("유저 정보 업데이트")
    class UserUpdateLogic {

        String userId;
        String userName;
        String email;
        String detailAddress;
        UserJoinRequest request;
        double[] doubleEx = new double[]{38.239329, 39.239329};

        @BeforeEach
        public void userSetUp() {
            userId = "userId";
            userName = "userName";
            email = "abc1234@abcde.abc";
            detailAddress = "상세 주소";
            request = new UserJoinRequest(userId, "password", userName,
                email, "010-2929-9192", UserRole.USER,
                "서울", "스트리트", "지역", "상세주소");
        }

        @DisplayName("유저 정보 변경 및 주소 변경 안함")
        @Test
        public void 유저정보변경_주소변경안함() {
            String newUserName = "newUser";
            email = "new@email.com";

            // given
            when(userRepository.findByUserId(userId)).thenReturn(
                Optional.of(User.from(UserDto.from(request, "password",
                    1111011400L, doubleEx[0], doubleEx[1]))));

            when(readAddressCsv.findAddressByDistrict(request.city(), request.street(),
                request.district())).thenReturn(
                new AddressDto(1111011400L,
                    request.city(), request.street(), request.district()));

//            when(googleApiCall.getGeocode(any(String.class))).thenReturn(doubleEx);

            UserUpdateRequest updateRequest = new UserUpdateRequest("password", newUserName,
                email, "010-2929-9192",
                null, null, null, null);

            // when
            UserResponse update = userService.update(userId, updateRequest);

            // then
            // 변경이 되었는지 확인한다.
            assertThat(update.userName()).isEqualTo(newUserName);
            assertThat(update.email()).isEqualTo(email);

            // 유저의 주소 정보는 전달안했으므로 메서드가 호출되면 안된다.
            verify(googleApiCall, times(0))
                .getGeocode(any(String.class));
        }

        @DisplayName("유저 정보 변경 및 주소 변경 함")
        @Test
        public void 유저정보변경_주소변경함() {
            String newUserName = "newUser";
            email = "new@email.com";

            // given
            when(userRepository.findByUserId(userId)).thenReturn(
                Optional.of(User.from(UserDto.from(request, "password",
                    1111011400L, doubleEx[0], doubleEx[1]))));

            when(readAddressCsv.findAddressByDistrict(any(String.class), any(String.class),
                any(String.class))).thenReturn(
                new AddressDto(1111011400L,
                    request.city(), request.street(), request.district()));

            when(googleApiCall.getGeocode(any(String.class))).thenReturn(doubleEx);

            UserUpdateRequest updateRequest = new UserUpdateRequest("password", newUserName,
                email, "010-2929-9192",
                "null", "null", null, null);

            // when
            UserResponse update = userService.update(userId, updateRequest);

            // then
            // 변경이 되었는지 확인한다.
            assertThat(update.userName()).isEqualTo(newUserName);
            assertThat(update.email()).isEqualTo(email);

            // 유저의 주소 정보는 전달안했으므로 메서드가 호출되면 안된다.
            verify(googleApiCall, times(1))
                .getGeocode(any(String.class));

        }
    }


}