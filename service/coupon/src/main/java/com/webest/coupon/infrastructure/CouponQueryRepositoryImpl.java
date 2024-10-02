package com.webest.coupon.infrastructure;

import static com.webest.coupon.domain.model.QCoupon.coupon;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webest.coupon.domain.model.Coupon;
import com.webest.coupon.domain.model.CouponSearchCondition;
import com.webest.coupon.domain.repository.CouponQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CouponQueryRepositoryImpl implements CouponQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Coupon> findCouponListByQuery(CouponSearchCondition condition, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        if (condition.startTime() != null) {
            builder.and(coupon.startTime.after(condition.startTime().atStartOfDay()));
        }

        if (condition.endTime() != null) {
            builder.and(coupon.endTime.before(condition.endTime().atStartOfDay()));
        }

        if (condition.ids() != null && !condition.ids().isEmpty()) {
            builder.and(coupon.couponId.in(condition.ids()));
        }

        List<Coupon> couponList = query.selectFrom(coupon)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(sortCoupon(pageable).toArray(OrderSpecifier[]::new))
            .fetch();

        Long totalCount = query.select(coupon.count())
            .from(coupon)
            .where(builder)
            .fetchOne();

        return PageableExecutionUtils.getPage(couponList, pageable, () -> totalCount);

    }

    private List<OrderSpecifier> sortCoupon(Pageable pageable) {

        if (pageable.getSort().isEmpty()) {
            return List.of(new OrderSpecifier<>(Order.DESC, coupon.createdAt));
        }

        return pageable.getSort().map(order -> {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            PathBuilder<Coupon> userPath = new PathBuilder<>(Coupon.class, "coupon");

            return new OrderSpecifier(direction,
                userPath.get(order.getProperty()));

        }).toList();
    }
}
