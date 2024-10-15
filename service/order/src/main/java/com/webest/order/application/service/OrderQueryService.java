package com.webest.order.application.service;

import com.webest.order.application.dtos.OrderSearchDto;
import com.webest.order.domain.events.OrderCreatedEvent;
import com.webest.order.domain.exception.ErrorCode;
import com.webest.order.domain.exception.OrderException;
import com.webest.order.domain.model.OrderStatus;
import com.webest.order.domain.service.UserService;
import com.webest.order.infrastructure.model.OrderQuery;
import com.webest.order.domain.repository.order.OrderQueryRepository;
import com.webest.order.presentation.response.OrderQueryResponse;
import com.webest.order.presentation.response.OrderResponse;
import com.webest.web.common.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    public void createOrderQuery(OrderCreatedEvent event) {

        OrderQuery orderQuery = OrderQuery.create(
                event.getId(),
                event.getStoreId(),
                event.getPaymentId(),
                event.getCouponId(),
                event.getUserId(),
                event.getOrderStatus(),
                event.getIsRequest(),
                event.getRequestsToStore(),
                event.getRequestsToRider(),
                event.getStoreAddressCode(),
                event.getStoreDetailAddress(),
                event.getArrivalAddressCode(),
                event.getArrivalDetailAddress(),
                event.getTotalQuantity(),
                event.getTotalProductPrice(),
                event.getCouponAppliedAmount(),
                event.getDeliveryTipAmount(),
                event.getTotalPaymentPrice(),
                event.getOrderProducts());

        orderQueryRepository.save(orderQuery);

    }


    @Transactional
    public OrderQueryResponse getOrderQuery(String userId, UserRole userRole, Long orderId) {

        return OrderQueryResponse.of(orderQueryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND)));
    }

    @Transactional
    public List<OrderQueryResponse> getAllOrderQueries(String userId, UserRole userRole) {

        List<OrderQuery> orderQueries = orderQueryRepository.findAll();

        return OrderQueryResponse.of(orderQueries);
    }

    /**
     * 주문 검색
     * @param userId 유저 아이디 값
     */
    @Transactional
    public Page<OrderQueryResponse> searchOrders(String userId, UserRole userRole, OrderSearchDto request, PageRequest pageRequest) {

        return orderQueryRepository.searchOrders(request, pageRequest)
                .map(OrderQueryResponse::of);
    }




    @Transactional
    public void cancelOrderQuery(Long orderId) {

        orderQueryRepository.findByOrderId(orderId)
                .map(orderQuery -> {

                    orderQuery.cancel();

                    return orderQueryRepository.save(orderQuery);  // 수정 후 저장
                }).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Transactional
    public void requestOrder(Long orderId) {

         orderQueryRepository.findByOrderId(orderId).map(order -> {

            order.requestOrder();

            return orderQueryRepository.save(order);
        }).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));

    }

    /**
     * 주문 확인 (주문 상태값이 CONFIRMING_ORDER -> PREPARING 변경)
     * @param orderId 주문 아이디 값
     */
    @Transactional
    public void preparing(Long orderId) {
        orderQueryRepository.findByOrderId(orderId).map(order -> {

            order.preparing();

            if(order.getOrderStatus() == OrderStatus.CONFIRMING_ORDER) {
                throw new OrderException(ErrorCode.ALREADY_CANCELLED_ORDER);
            }

            return orderQueryRepository.save(order);
        }).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
    }

    /**
     * 주문 완료(배달완료 되었을때 이벤트를 받아서 처리)
     * @param orderId 주문 완료 주문의 ID
     */
    @Transactional
    public void completeOrder(Long orderId) {

         orderQueryRepository.findByOrderId(orderId).map(order -> {


            // payment 기능 완료 될 시 paymentStatus에 따라 주문취소 혹은 주문완료 구현

            order.complete();

            return orderQueryRepository.save(order);
        }).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));

    }

    /**
     * 주문 삭제
     * @param orderId 삭제할 주문의 ID
     */
    @Transactional
    public void deleteOrder(Long orderId) {
        orderQueryRepository.findByOrderId(orderId).map(order -> {

            order.delete();

            return orderQueryRepository.save(order);
        }).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
    }





}
