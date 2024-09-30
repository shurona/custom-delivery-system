package com.webest.order.application.service;

import com.webest.order.application.dtos.OrderDto;
import com.webest.order.application.dtos.OrderProductDto;
import com.webest.order.domain.model.Order;
import com.webest.order.domain.model.OrderProduct;
import com.webest.order.domain.repository.orderproduct.OrderProductRepository;
import com.webest.order.presentation.response.OrderProductResponse;
import com.webest.order.presentation.response.OrderResponse;
import com.webest.web.exception.ApplicationException;
import com.webest.web.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;


    @Transactional
    public List<OrderProduct> createOrderProduct(List<OrderProductDto> requestList) {
        // 각 OrderProductDto를 OrderProduct로 변환 후 저장
        List<OrderProduct> orderProducts = requestList.stream()
                .map(request -> OrderProduct.create(
                        request.productId(),
                        request.quantity(),
                        request.price()
                ))
                .collect(Collectors.toList());

        // 리스트로 저장
        return orderProductRepository.saveAll(orderProducts);
    }

    @Transactional
    public OrderProductResponse updateOrderProduct(Long orderProductId, OrderProductDto request) {

        return orderProductRepository.findById(orderProductId).map(orderProduct -> {
            orderProduct.update(
                    request.productId(),
                    request.quantity(),
                    request.price(),
                    request.totalPrice());
            return OrderProductResponse.of(orderProduct);
        }).orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_PRODUCT_NOT_FOUND));
    }


}
