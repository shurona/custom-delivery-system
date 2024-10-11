package com.webest.delivery.infrastructure.repostiory;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webest.delivery.application.dtos.DeliveryRecordDto;
import com.webest.delivery.application.dtos.DeliveryRecordSearchDto;
import com.webest.delivery.domain.model.Delivery;
import com.webest.delivery.domain.model.DeliveryRecord;
import com.webest.delivery.domain.model.QDelivery;
import com.webest.delivery.domain.model.QDeliveryRecord;
import com.webest.delivery.domain.repository.DeliveryRecordCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryRecordRepositoryImpl implements DeliveryRecordCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<DeliveryRecord> searchDeliveryRecord(DeliveryRecordSearchDto request, Pageable pageable) {

        QDeliveryRecord deliveryRecord = QDeliveryRecord.deliveryRecord;

        BooleanBuilder builder = new BooleanBuilder();

        // 조건: deliveryId
        if (request.deliveryId() != null) {
            builder.and(deliveryRecord.deliveryId.eq(request.deliveryId()));
        }

        // 조건: orderId
        if (request.orderId() != null) {
            builder.and(deliveryRecord.orderId.eq(request.orderId()));
        }

        // 조건: riderId
        if (request.riderId() != null) {
            builder.and(deliveryRecord.riderId.eq(request.riderId()));
        }

        // 조건: deliveryStatus
        if (request.deliveryStatus() != null) {
            builder.and(deliveryRecord.deliveryStatus.eq(request.deliveryStatus()));
        }

        // 조건: deliveryFeeAmount
        if (request.deliveryFeeAmount() != null) {
            builder.and(deliveryRecord.deliveryFeeAmount.eq(request.deliveryFeeAmount()));
        }

        if (request.createdAt() != null) {
            builder.and(deliveryRecord.createdAt.eq(request.createdAt()));
        }

        if (request.createdBy() != null) {
            builder.and(deliveryRecord.createdBy.eq(request.createdBy()));
        }


        // Query 생성
        JPAQuery<DeliveryRecord> query = jpaQueryFactory
                .selectFrom(deliveryRecord)
                .where(builder)
                .offset(pageable.getOffset())  // 페이징 처리
                .limit(pageable.getPageSize()); // 페이징 처리

        long total = query.fetch().size();  // 전체 검색 결과 수

        List<DeliveryRecord> results = query.fetch();  // 실제 검색 결과 목록

        return new PageImpl<>(results, pageable, total);

    }
}
