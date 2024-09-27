package com.webest.order.application.service;

import com.webest.order.application.dtos.OrderDto;
import com.webest.order.domain.events.OrderCanceledEvent;
import com.webest.order.domain.events.OrderCompletedEvent;
import com.webest.order.domain.model.OrderStatus;
import com.webest.order.infrastructure.messaging.events.PaymentCompletedEvent;
import com.webest.order.domain.model.Order;
import com.webest.order.domain.repository.OrderRepository;
import com.webest.order.presentation.response.OrderResponse;
import com.webest.web.exception.ApplicationException;
import com.webest.web.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderEventService orderEventService;

    /**
     * 주문 생성
     * @param request 주문 생성에 필요한 정보를 담은 OrderDto
     * @return 저장된 주문 데이터를 담은 OrderResponse
     */
    @Transactional
    public OrderResponse createOrder(OrderDto request) {

        Order order = Order.create(
                request.storeId(),
                request.paymentId(),
                request.couponId(),
                request.userId(),
                request.orderStatus(),
                request.isRequest(),
                request.totalQuantity(),
                request.totalProductPrice(),
                request.couponAppliedAmount(),
                request.deliveryTipAmount(),
                request.totalPaymentPrice()
        );

        // 주문 저장
        orderRepository.save(order);

        // 주문 생성시 이벤트 발생
        orderEventService.publishOrderCreatedEvent(order.createOrderCreatedEvent());

        return OrderResponse.of(order);
    }


    /**
     * 주문 완료
     */
    @Transactional
    public void completeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND));

        OrderCompletedEvent completedEvent = order.completed();

        orderRepository.save(order);

        orderEventService.publishOrderCompletedEvent(completedEvent);

    }

    /**
     * 주문 단건 조회
     * @param orderId 조회할 주문의 고유 ID (Long)
     * @return 조회된 주문 데이터를 담은 OrderResponse
     */
    @Transactional
    public OrderResponse getOrder(Long orderId) {
        return OrderResponse.of(orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND)));
    }

    /**
     * 주문 전체 조회
     * @return 모든 주문을 담고 있는 OrderResponse 리스트
     */
    @Transactional
    public List<OrderResponse> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        return OrderResponse.of(orders);
    }

    /**
     * 주문 수정
     * @param orderId 주문을 업데이트하기 위한 ID
     * @param request 주문 업데이트에 필요한 데이터
     * @return 수정된 주문 데이터를 담은 OrderResponse
     */
    @Transactional
    public OrderResponse updateOrder(Long orderId, OrderDto request) {

        return orderRepository.findById(orderId).map(order -> {
            order.update(
                    request.storeId(),
                    request.paymentId(),
                    request.couponId(),
                    request.userId(),
                    request.orderStatus(),
                    request.isRequest(),
                    request.totalQuantity(),
                    request.totalProductPrice(),
                    request.couponAppliedAmount(),
                    request.deliveryTipAmount(),
                    request.totalPaymentPrice());
            return OrderResponse.of(order);
        }).orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND));
    }

    /**
     * 주문 삭제
     * @param orderId 삭제할 주문의 ID
     */
    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.findById(orderId).map(order -> {
            order.delete();
            return OrderResponse.of(order);
        }).orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND));
    }




}

