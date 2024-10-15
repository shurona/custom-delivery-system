package com.webest.coupon.infrastructure;

import static com.webest.coupon.domain.model.QCoupon.coupon;
import static com.webest.coupon.domain.model.QCouponUser.couponUser;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webest.coupon.common.exception.CouponErrorCode;
import com.webest.coupon.common.exception.CouponException;
import com.webest.coupon.domain.dtos.CouponByUserDto;
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

    @Override
    public List<CouponByUserDto> findCouponListByUserId(String userId, Boolean used) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(couponUser.userId.eq(userId));

        if (used != null) {
            builder.and(couponUser.used.eq(used));
        }

        return query.select(
                Projections.constructor(CouponByUserDto.class,
                    couponUser.couponUserId, coupon.couponId, coupon.content,
                    couponUser.expiredTime, couponUser.used))
            .from(coupon)
            .innerJoin(coupon.couponUserList, couponUser)
            .where(builder)
            .orderBy(couponUser.expiredTime.desc())
            .fetch();
    }

    @SuppressWarnings("unchecked")
    private List<OrderSpecifier> sortCoupon(Pageable pageable) {

        if (pageable.getSort().isEmpty()) {
            return List.of(new OrderSpecifier<>(Order.DESC, coupon.createdAt));
        }

        return pageable.getSort()
            .map(order -> {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                return switch (order.getProperty()) {
                    case "createdAt" -> new OrderSpecifier(direction, coupon.createdAt);
                    case "startTime" -> new OrderSpecifier(direction, coupon.startTime);
                    case "endTime" -> new OrderSpecifier(direction, coupon.endTime);
                    case "maxQuantity" -> new OrderSpecifier(direction, coupon.maxQuantity);
                    default -> throw new CouponException(CouponErrorCode.INVALID_INPUT);
                };

            }).toList();
    }
}
