package com.webest.store.store.infrastructure.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import com.webest.app.address.service.AddressDto;
import com.webest.store.application.config.QueryDslConfig;
import com.webest.store.store.domain.model.Store;
import com.webest.store.store.domain.model.StoreAddress;
import jakarta.persistence.EntityManager;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(QueryDslConfig.class)
class CustomStoreRepositoryImplTest {

    @Autowired
    private CustomStoreRepositoryImpl storeRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("지역으로 가게 조회")
    @Test
    public void 지역_가게_조회() {
        //given
        Long addressCodeOne = 1111012200L;

        String ownerOne = "ownerOne";
        String ownerTwo = "ownerTwo";

        Store store1 = createStore(ownerOne, "Store 1", addressCodeOne, 1L);
        Store store2 = createStore(ownerTwo, "Store 2", addressCodeOne, 2L);

        // 배달 가능한 주소를 등록한다.
        store1.registerDeliveryArea(List.of(addressCodeOne));
        store2.registerDeliveryArea(List.of(addressCodeOne));

        em.persist(store1);
        em.persist(store2);

        //when
        List<Store> stores = storeRepository.findStoresByAddressCode(addressCodeOne);

        System.out.println(stores);

        //then

        assertThat(stores.size()).isEqualTo(2);
        assertThat(List.of(ownerOne, ownerTwo)).contains(stores.get(0).getOwnerId());
        assertThat(Stream.of(ownerOne, ownerTwo)
            .anyMatch(d -> d.equals(stores.get(0).getOwnerId()))).isTrue();
    }

    @DisplayName("법정 코드 및 카테고리로 가게 검색")
    @Test
    public void 코드_카테고리_가게조회() {
        // given
        Long addressCodeOne = 1111012200L;

        String ownerOne = "ownerOne";
        String ownerTwo = "ownerTwo";
        String ownerThree = "ownerThree";

        Store store1 = createStore(ownerOne, "Store 1", addressCodeOne, 1L);
        Store store2 = createStore(ownerTwo, "Store 2", addressCodeOne, 2L);
        Store store3 = createStore(ownerThree, "Store 3", addressCodeOne, 2L);

        // 배달 가능한 주소를 등록한다.
        store1.registerDeliveryArea(List.of(addressCodeOne));
        store2.registerDeliveryArea(List.of(addressCodeOne));
        store3.registerDeliveryArea(List.of(addressCodeOne));

        em.persist(store1);
        em.persist(store2);
        em.persist(store3);

        // when
        List<Store> storeList = storeRepository.findStoresByAddressCodeAndCategoryIdAndName(
            addressCodeOne, 2L, "tore");

        // then
        assertThat(storeList.size()).isEqualTo(2);
        assertThat(
            storeList.stream().anyMatch(store -> store.getOwnerId().equals(ownerOne))).isFalse();

    }

    @DisplayName("카테고리 및 가게 검색")
    @Test
    public void 카테고리_검색() {
        // given
        Long addressCodeOne = 1111012200L;

        String ownerOne = "ownerOne";
        String ownerTwo = "ownerTwo";
        String ownerThree = "ownerThree";

        Store store1 = createStore(ownerOne, "Store 1", addressCodeOne, 1L);
        Store store2 = createStore(ownerTwo, "Store 2", addressCodeOne, 2L);
        Store store3 = createStore(ownerThree, "Store 3", addressCodeOne, 2L);

        // 배달 가능한 주소를 등록한다.
        store1.registerDeliveryArea(List.of(addressCodeOne));
        store2.registerDeliveryArea(List.of(addressCodeOne));
        store3.registerDeliveryArea(List.of(addressCodeOne));

        em.persist(store1);
        em.persist(store2);
        em.persist(store3);

        // when
        List<Store> storeList = storeRepository.findStoresByCategoryIdAndName(3L, "tore");

        // then
        assertThat(storeList.size()).isEqualTo(0);
        assertThat(
            storeList.stream().anyMatch(store -> store.getOwnerId().equals(ownerOne))).isFalse();

    }


    private Store createStore(String ownerId, String name, Long addressCode, Long categoryId) {
        Store store = Store.of(
            name,
            ownerId,
            categoryId,
            30,
            10000.0,
            "010-2020-2200",
            LocalTime.now(),
            LocalTime.now().plusHours(6),
            1000.0
        );

        store.updateAddress(
            StoreAddress.from(
                AddressDto.from(new String[]{String.valueOf(addressCode), "서울특별시", "종로구", "청진동"}),
                "청진의사"),
            52.22, 223.22);
        return store;
    }


}