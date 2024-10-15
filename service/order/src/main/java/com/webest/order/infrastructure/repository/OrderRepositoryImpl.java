package com.webest.order.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webest.order.application.dtos.OrderDto;
import com.webest.order.application.dtos.OrderProductDto;
import com.webest.order.application.dtos.OrderSearchDto;
import com.webest.order.domain.model.Order;
import com.webest.order.domain.model.QOrder;
import com.webest.order.domain.repository.order.OrderCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Order> searchOrders(OrderSearchDto request, Pageable pageable) {

        QOrder order = QOrder.order;


        BooleanBuilder builder = new BooleanBuilder();

        // storeId가 있을 경우 검색 조건 추가
        if (request.storeId() != null) {
            builder.and(order.storeId.eq(request.storeId()));
        }

        // paymentId가 있을 경우 검색 조건 추가
        if (request.paymentId() != null) {
            builder.and(order.paymentId.eq(request.paymentId()));
        }

        // couponId가 있을 경우 검색 조건 추가
        if (request.couponId() != null) {
            builder.and(order.couponId.eq(request.couponId()));
        }

        // userId가 있을 경우 검색 조건 추가
        if (request.userId() != null) {
            builder.and(order.userId.eq(request.userId()));
        }

        // orderStatus가 있을 경우 검색 조건 추가
        if (request.orderStatus() != null) {
            builder.and(order.orderStatus.eq(request.orderStatus()));
        }

        // isRequest가 있을 경우 검색 조건 추가
        if (request.isRequest() != null) {
            builder.and(order.isRequest.eq(request.isRequest()));
        }

        // totalQuantity가 있을 경우 검색 조건 추가
        if (request.totalQuantity() != null) {
            builder.and(order.totalQuantity.eq(request.totalQuantity()));
        }

        // totalProductPrice가 있을 경우 검색 조건 추가
        if (request.totalProductPrice() != null) {
            builder.and(order.totalProductPrice.eq(request.totalProductPrice()));
        }

        // couponAppliedAmount가 있을 경우 검색 조건 추가
        if (request.couponAppliedAmount() != null) {
            builder.and(order.couponAppliedAmount.eq(request.couponAppliedAmount()));
        }

        // deliveryTipAmount가 있을 경우 검색 조건 추가
        if (request.deliveryTipAmount() != null) {
            builder.and(order.deliveryTipAmount.eq(request.deliveryTipAmount()));
        }

        // totalPaymentPrice가 있을 경우 검색 조건 추가
        if (request.totalPaymentPrice() != null) {
            builder.and(order.totalPaymentPrice.eq(request.totalPaymentPrice()));
        }


        // Query 생성
        JPAQuery<Order> query = jpaQueryFactory
                .selectFrom(order)
                .where(builder)
                .offset(pageable.getOffset())  // 페이징 처리
                .limit(pageable.getPageSize()); // 페이징 처리

        // 결과 조회
        long total = query.fetch().size();  // 전체 검색 결과 수
        List<Order> results = query.fetch();  // 실제 검색 결과 목록

        return new PageImpl<>(results, pageable, total);
    }
}
