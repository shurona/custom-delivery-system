package com.webest.store.store.infrastructure.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.webest.store.store.domain.model.QStore.store;
import com.webest.store.store.domain.model.Store;
import com.webest.store.store.domain.repository.CustomStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomStoreRepositoryImpl implements CustomStoreRepository {

    private final JPAQueryFactory queryFactory;

    // 법정 코드로 상점 검색
    @Override
    public List<Store> findStoresByAddressCode(Long addressCode) {
        return queryFactory.selectFrom(store)
                .where(store.addressCodeList.contains(addressCode))
                .fetch(); // 쿼리 결과를 리스트 형태로 반환
    }

    // 법정 코드와 상점 카테고리 별 검색
    @Override
    public List<Store> findStoresByAddressCodeAndCategory(Long categoryId, Long addressCode) {
        return queryFactory.selectFrom(store)
                .where(store.addressCodeList.contains(addressCode)
                        .and(store.categoryId.eq(categoryId)))
                .fetch();
    }


}
