package com.webest.rider.infrastructure;

import static com.webest.rider.domain.model.QRider.rider;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webest.rider.domain.model.Rider;
import com.webest.rider.domain.model.RiderSearchCondition;
import com.webest.rider.domain.repository.RiderQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class RiderQueryRepositoryImpl implements RiderQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Rider> findRiderListByQuery(Pageable pageable,
        RiderSearchCondition searchCondition) {

        BooleanBuilder builder = new BooleanBuilder();

        if (searchCondition.transportation() != null) {
            builder.and(rider.transportation.eq(searchCondition.transportation()));
        }

        List<Rider> riderList = query.select(rider)
            .from(rider)
            .where(builder)
            .orderBy(sortUser(pageable).toArray(OrderSpecifier[]::new))
            .fetch();

        JPAQuery<Long> totalCount = query.select(rider.count())
            .from(rider)
            .where(builder);

        return PageableExecutionUtils.getPage(riderList, pageable, totalCount::fetchOne);
    }

    private List<OrderSpecifier> sortUser(Pageable pageable) {

        if (pageable.getSort().isEmpty()) {
            return List.of(new OrderSpecifier<>(Order.DESC, rider.createdAt));
        }

        return pageable.getSort().map(order -> {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

//            try {
//                Rider.class.getDeclaredField(order.getProperty());
//            } catch (NoSuchFieldException e) {
//                throw new IllegalArgumentException("Invalid sort property: " + order.getProperty());
//            }

            PathBuilder<Rider> userPath = new PathBuilder<>(Rider.class, "rider");

            return new OrderSpecifier(direction,
                userPath.get(order.getProperty()));

        }).toList();
    }
}
