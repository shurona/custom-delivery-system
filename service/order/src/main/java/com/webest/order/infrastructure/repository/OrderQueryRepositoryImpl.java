package com.webest.order.infrastructure.repository;

import com.webest.order.application.dtos.OrderSearchDto;
import com.webest.order.domain.repository.order.OrderQueryCustomRepository;
import com.webest.order.infrastructure.model.OrderQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<OrderQuery> searchOrders(OrderSearchDto request, Pageable pageable) {
        Query query = new Query();

        // 검색 조건 추가 (record의 필드 접근)
        if (request.orderId() != null) {
            query.addCriteria(Criteria.where("orderId").is(request.orderId()));
        }

        if (request.storeId() != null) {
            query.addCriteria(Criteria.where("storeId").is(request.storeId()));
        }

        if (request.paymentId() != null) {
            query.addCriteria(Criteria.where("paymentId").is(request.paymentId()));
        }

        if (request.couponId() != null) {
            query.addCriteria(Criteria.where("couponId").is(request.couponId()));
        }

        if (request.userId() != null) {
            query.addCriteria(Criteria.where("userId").is(request.userId()));
        }

        if (request.orderStatus() != null) {
            query.addCriteria(Criteria.where("orderStatus").is(request.orderStatus().toString()));
        }

        if (request.isRequest() != null) {
            query.addCriteria(Criteria.where("isRequest").is(request.isRequest()));
        }

        if (request.requestsToStore() != null) {
            query.addCriteria(Criteria.where("requestsToStore").regex(".*" + request.requestsToStore() + ".*", "i"));
        }

        if (request.requestsToRider() != null) {
            query.addCriteria(Criteria.where("requestsToRider").regex(".*" + request.requestsToRider() + ".*", "i"));
        }

        if (request.totalQuantity() != null) {
            query.addCriteria(Criteria.where("totalQuantity").is(request.totalQuantity()));
        }

        if (request.totalProductPrice() != null) {
            query.addCriteria(Criteria.where("totalProductPrice").is(request.totalProductPrice()));
        }

        if (request.couponAppliedAmount() != null) {
            query.addCriteria(Criteria.where("couponAppliedAmount").is(request.couponAppliedAmount()));
        }

        if (request.deliveryTipAmount() != null) {
            query.addCriteria(Criteria.where("deliveryTipAmount").is(request.deliveryTipAmount()));
        }

        if (request.totalPaymentPrice() != null) {
            query.addCriteria(Criteria.where("totalPaymentPrice").is(request.totalPaymentPrice()));
        }



        // 페이지 정보 적용
        long total = mongoTemplate.count(query, OrderQuery.class);  // 총 문서 수 계산
        query.with(pageable);  // Pageable 적용

        // 결과 가져오기
        List<OrderQuery> orderQueries = mongoTemplate.find(query, OrderQuery.class);

        // Page 객체로 반환
        return new PageImpl<>(orderQueries, pageable, total);
    }
}
