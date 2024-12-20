package com.webest.store.store.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.webest.app.address.csv.ReadAddressCsv;
import com.webest.store.store.domain.model.Store;
import com.webest.store.store.domain.repository.CustomStoreRepository;
import com.webest.store.store.domain.repository.StoreRepository;
import com.webest.store.store.exception.StoreErrorCode;
import com.webest.store.store.exception.StoreException;
import com.webest.store.store.infrastructure.naver.NaverGeoClient;
import com.webest.store.store.infrastructure.user.UserClient;
import com.webest.store.store.presentation.dto.CreateStoreRequest;
import com.webest.store.store.presentation.dto.StoreResponse;
import com.webest.web.common.UserRole;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UnitStoreServiceTest {

    @Mock
    private StoreRepository storeRepository;
    @Mock
    private CustomStoreRepository customStoreRepository;
    @Mock
    private NaverGeoClient naverGeoClient;
    @Mock
    private UserClient userClient;
    @Mock
    private ReadAddressCsv readAddressCsv;

    @Mock
    private StoreCacheService storeCacheService;
    @Spy
    private GeoOperation geoOperation;

    @InjectMocks
    private StoreService storeService;

    @DisplayName("Store 저장 쿼리 테스트")
    @Nested
    public class StoreCRUD {

        CreateStoreRequest createStoreRequest;
        String storeName = "storeGood";

        @BeforeEach
        public void storeSetup() {
            // given

            createStoreRequest = new CreateStoreRequest(
                storeName,
                1L,
                30,
                10000.0,
                "010-2020-2200",
                LocalTime.now(),
                LocalTime.now().plusHours(6),
                1000.0
            );
        }

        @Test
        public void 정상_생성_Flow() {
            // given
            String ownerId = "ownerId";

            Store store = Mockito.mock(Store.class);

            // when
            StoreResponse storeResponse = storeService.saveStore(createStoreRequest, ownerId,
                UserRole.OWNER);

            // then
            assertThat(storeResponse.ownerId()).isEqualTo(ownerId);
            // 위도 경도는 생성 시 저장되지 않음
            assertThat(storeResponse.latitude()).isEqualTo(null);

        }

        @Test
        public void 미인증유저_가게_생성_확인() {
            // given
            String ownerId = "ownerId";

            // when
            // User 시 에러
            Throwable throwableUser = catchThrowable(() -> {
                storeService.saveStore(createStoreRequest, ownerId + "-user", UserRole.USER);
            });

            // Rider 시 에러
            Throwable throwableRider = catchThrowable(() -> {
                storeService.saveStore(createStoreRequest, ownerId + "-rider", UserRole.RIDER);
            });

            // then
            assertThat(throwableUser)
                .isInstanceOf(StoreException.class)
                .hasMessage(StoreErrorCode.UNAUTHORIZED_ACCESS.getMessage());

            assertThat(throwableRider)
                .isInstanceOf(StoreException.class)
                .hasMessage(StoreErrorCode.UNAUTHORIZED_ACCESS.getMessage());
        }
    }


}