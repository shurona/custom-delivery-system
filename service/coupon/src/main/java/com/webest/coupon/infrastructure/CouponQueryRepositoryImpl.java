package com.webest.coupon.infrastructure;

import static com.webest.coupon.domain.model.QCoupon.coupon;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
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

        if (condition.ids() != null) {
            builder.and(coupon.couponId.in(condition.ids()));
        }

        List<Coupon> couponList = query.selectFrom(coupon)
            .where(builder)
            .fetch();

        JPAQuery<Long> totalCount = query.select(coupon.count())
            .from(coupon)
            .where(builder);

        return PageableExecutionUtils.getPage(couponList, pageable, totalCount::fetchOne);

    }
}
