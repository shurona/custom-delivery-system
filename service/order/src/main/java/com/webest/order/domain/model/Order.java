package com.webest.order.domain.model;


import com.webest.app.jpa.BaseEntity;
import com.webest.order.domain.events.*;
import com.webest.order.domain.service.UserService;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;

    private Long paymentId;

    private Long couponId;

    private Long userId;

    @Enumerated(EnumType.STRING)  // Enum을 문자열로 DB에 저장
    private OrderStatus orderStatus;

    private Boolean isRequest; // 주문 -> 배달 요청여부

    private String requests; // 요청 사항

    private Long arrivalAddressCode;

    private String arrivalDetailAddress;

    private Integer totalQuantity;

    private Double totalProductPrice;

    private Double couponAppliedAmount;

    private Double deliveryTipAmount;

    private Double totalPaymentPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    public static Order create(Long storeId,
                               Long paymentId,
                               Long couponId,
                               Long userId,
                               OrderStatus orderStatus,
                               Boolean isRequest,
                               Integer totalQuantity,
                               Double totalProductPrice,
                               Double couponAppliedAmount,
                               Double deliveryTipAmount,
                               Double totalPaymentPrice,
                               List<OrderProduct> orderProducts)
    {

        Order order = new Order();
        order.storeId = storeId;
        order.paymentId = paymentId;
        order.couponId = couponId;
        order.userId = userId;
        order.orderStatus = orderStatus;
        order.isRequest = isRequest;

        order.totalQuantity = calculateTotalQuantity(orderProducts);

        order.totalProductPrice = calculateTotalProductPrice(orderProducts);

        order.couponAppliedAmount = couponAppliedAmount;

        order.deliveryTipAmount = deliveryTipAmount;

        order.totalPaymentPrice = calculateTotalPaymentPrice(order.totalProductPrice, couponAppliedAmount, deliveryTipAmount);

        order.orderProducts = orderProducts;

        orderProducts.forEach(orderProduct -> orderProduct.setOrder(order));

        return order;
    }

    public void update(Long storeId,
                       Long paymentId,
                       Long couponId,
                       Long userId,
                       OrderStatus orderStatus,
                       Boolean isRequest,
                       Integer totalQuantity,
                       Double totalProductPrice,
                       Double couponAppliedAmount,
                       Double deliveryTipAmount,
                       Double totalPaymentPrice) {
        this.storeId = storeId;
        this.paymentId = paymentId;
        this.couponId = couponId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.totalQuantity = calculateTotalQuantity(orderProducts);
        this.totalProductPrice = calculateTotalProductPrice(orderProducts);
        this.couponAppliedAmount = couponAppliedAmount;
        this.deliveryTipAmount = deliveryTipAmount;
        this.totalPaymentPrice = calculateTotalPaymentPrice(calculateTotalProductPrice(orderProducts), couponAppliedAmount, deliveryTipAmount);
    }

    public void delete() {
        this.isDeleted = true;
    }

    // 배달로 요청
    public void requestOrder() {
        this.isRequest = true;
    }

    public OrderCreatedEvent createdEvent() {
        return new OrderCreatedEvent(
                this.id,
                this.storeId,
                this.paymentId,
                this.couponId,
                this.userId,
                this.orderStatus,
                this.isRequest,
                this.requests,
                this.arrivalAddressCode,
                this.arrivalDetailAddress,
                this.totalQuantity,
                this.totalProductPrice,
                this.couponAppliedAmount,
                this.deliveryTipAmount,
                this.totalPaymentPrice);
    }

    public OrderUpdatedEvent updatedEvent() {
        return new OrderUpdatedEvent(
                this.id,
                this.storeId,
                this.paymentId,
                this.couponId,
                this.userId,
                this.orderStatus,
                this.isRequest,
                this.requests,
                this.arrivalAddressCode,
                this.arrivalDetailAddress,
                this.totalQuantity,
                this.totalProductPrice,
                this.couponAppliedAmount,
                this.deliveryTipAmount,
                this.totalPaymentPrice);
    }

    public OrderCompletedEvent completed() {
        this.orderStatus = OrderStatus.PAYMENT_COMPLETED;
        return new OrderCompletedEvent(
                this.id,
                this.storeId,
                this.paymentId,
                this.couponId,
                this.userId,
                this.orderStatus,
                this.isRequest,
                this.requests,
                this.arrivalAddressCode,
                this.arrivalDetailAddress,
                this.totalQuantity,
                this.totalProductPrice,
                this.couponAppliedAmount,
                this.deliveryTipAmount,
                this.totalPaymentPrice);
        }

    public OrderCanceledEvent canceledEvent() {
        this.orderStatus = OrderStatus.PAYMENT_CANCELED;
        return new OrderCanceledEvent(
                this.id,
                this.storeId,
                this.paymentId,
                this.couponId,
                this.userId,
                this.orderStatus,
                this.isRequest,
                this.requests,
                this.arrivalAddressCode,
                this.arrivalDetailAddress,
                this.totalQuantity,
                this.totalProductPrice,
                this.couponAppliedAmount,
                this.deliveryTipAmount,
                this.totalPaymentPrice);
    }

    public OrderRequestedEvent requestedEvent() {
        this.orderStatus = OrderStatus.DELIVERING;
        return new OrderRequestedEvent(
                this.id,
                this.storeId,
                this.paymentId,
                this.couponId,
                this.userId,
                this.orderStatus,
                this.isRequest,
                this.requests,
                this.arrivalAddressCode,
                this.arrivalDetailAddress,
                this.totalQuantity,
                this.totalProductPrice,
                this.couponAppliedAmount,
                this.deliveryTipAmount,
                this.totalPaymentPrice);
    }

    // 각 OrderProduct의 quantity 값을 더해 totalQuantity 계산
    private static Integer calculateTotalQuantity(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .mapToInt(OrderProduct::getQuantity)
                .sum();
    }

    // 각 OrderProduct의 totalPrice 값을 더해 totalProductPrice 계산
    private static Double calculateTotalProductPrice(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .mapToDouble(OrderProduct::getTotalPrice)
                .sum();
    }

    // totalProductPrice +
    private static Double calculateTotalPaymentPrice(Double totalProductPrice, Double couponAppliedAmount, Double deliveryTipAmount) {
        return totalProductPrice + deliveryTipAmount - couponAppliedAmount;
    }

}
