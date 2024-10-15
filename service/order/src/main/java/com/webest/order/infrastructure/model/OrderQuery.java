package com.webest.order.infrastructure.model;

import com.querydsl.core.annotations.QueryEntity;
import com.webest.order.application.dtos.OrderProductDto;
import com.webest.order.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class OrderQuery extends BaseDocument {


    @Id
    private String id;

    private Long orderId;

    private Long storeId;

    private Long paymentId;

    private Long couponId;

    private String userId;

    @Enumerated(EnumType.STRING)  // Enum을 문자열로 DB에 저장
    private OrderStatus orderStatus;

    private Boolean isRequest; // 주문 -> 배달 요청여부

    private String requestsToStore; // 가게에게 요청사항

    private String requestsToRider; // 배송원에게 요청사항

    private Long storeAddressCode; // 가게 주소코드

    private String storeDetailAddress; // 가게 상세주소

    private Long arrivalAddressCode; // 도착 주소코드

    private String arrivalDetailAddress; // 도착 상세주소

    private Integer totalQuantity; // 총 수량

    private Double totalProductPrice; // 총 상품 가격

    private Double couponAppliedAmount; // 쿠폰 적용 가격

    private Double deliveryTipAmount; // 배달팁 가격

    private Double totalPaymentPrice; // 총 결제 가격

    private Double deliveryFeeAmount; // 배달료

    private List<OrderProductQuery> orderProductQueries;


    public static OrderQuery create(Long orderId,
                                    Long storeId,
                                    Long paymentId,
                                    Long couponId,
                                    String userId,
                                    OrderStatus orderStatus,
                                    Boolean isRequest,
                                    String requestsToStore,
                                    String requestsToRider,
                                    Integer totalQuantity,
                                    Double totalProductPrice,
                                    Double couponAppliedAmount,
                                    Double deliveryTipAmount,
                                    Double totalPaymentPrice,
                                    List<OrderProductDto> orderProductQueries)
    {

        OrderQuery orderQuery = new OrderQuery();
        orderQuery.orderId = orderId;
        orderQuery.storeId = storeId;
        orderQuery.paymentId = paymentId;
        orderQuery.couponId = couponId;
        orderQuery.userId = userId;
        orderQuery.orderStatus = orderStatus;
        orderQuery.isRequest = isRequest;
        orderQuery.requestsToStore = requestsToStore;
        orderQuery.requestsToRider = requestsToRider;
        orderQuery.totalQuantity = totalQuantity;
        orderQuery.totalProductPrice = totalProductPrice;
        orderQuery.couponAppliedAmount = couponAppliedAmount;
        orderQuery.deliveryTipAmount = deliveryTipAmount;
        orderQuery.totalPaymentPrice = totalPaymentPrice;
        orderQuery.orderProductQueries = OrderProductQuery.of(orderProductQueries);
        return orderQuery;
    }

    public void cancel() {
        this.orderStatus = OrderStatus.CANCEL;
    }

    // 주문 확인중에서 음식 준비중으로 변경 (CONFIRMING_ORDER -> PREPARING)
    public void preparing() {
        this.orderStatus = OrderStatus.PREPARING;
    }

    // 주문 완료
    public void complete() {
        this.orderStatus = OrderStatus.COMPLETE;
    }


    // 배달로 요청
    public void requestOrder() {
        this.orderStatus = OrderStatus.DELIVERING;
        this.isRequest = true;
    }

    // 논리 삭제 처리 메서드
    public void delete() {
        this.isDeleted = true;
    }

}
