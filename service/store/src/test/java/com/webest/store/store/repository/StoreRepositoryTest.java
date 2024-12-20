package com.webest.store.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.webest.store.application.config.QueryDslConfig;
import com.webest.store.store.domain.model.Store;
import com.webest.store.store.domain.repository.CustomStoreRepository;
import com.webest.store.store.domain.repository.StoreRepository;
import com.webest.store.store.presentation.dto.CreateStoreRequest;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@ExtendWith(MockitoExtension.class)
@Import(QueryDslConfig.class)
@DataJpaTest
public class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private CustomStoreRepository customStoreRepository;

    @DisplayName("기본 테스트")
    @Test
    public void 디비_기본_테스트() {
        // given
        String ownerId = "ownerId";
        Store store = Store.of(
            "name",
            ownerId,
            1L,
            30,
            10000.0,
            "010-2020-2200",
            LocalTime.now(),
            LocalTime.now().plusHours(6),
            1000.0
        );

        // when
        Store save = storeRepository.save(store);
        Store store1 = storeRepository.findById(save.getId()).orElseThrow();

        // then
        assertThat(ownerId).isEqualTo(store1.getOwnerId());
    }

    @Nested
    public class Default {

        CreateStoreRequest createStoreRequest;

        @BeforeEach
        public void storeSetup() {
            // given
            String storeName = "storeGood";
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
            Store store = Store.of(
                "name",
                ownerId,
                1L,
                30,
                10000.0,
                "010-2020-2200",
                LocalTime.now(),
                LocalTime.now().plusHours(6),
                1000.0
            );
            storeRepository.save(store);


        }

        @Test
        public void 정상_생성_Flow_Two() {
            // given
            String ownerId = "ownerId";
            Store store = Store.of(
                "name",
                ownerId,
                1L,
                30,
                10000.0,
                "010-2020-2200",
                LocalTime.now(),
                LocalTime.now().plusHours(6),
                1000.0
            );
            storeRepository.save(store);

        }
    }
}
