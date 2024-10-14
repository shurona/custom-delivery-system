package com.webest.order.domain.repository.order;

import com.webest.order.application.dtos.OrderDto;
import com.webest.order.application.dtos.OrderSearchDto;
import com.webest.order.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderCustomRepository {
    Page<Order> searchOrders(OrderSearchDto request, Pageable pageable);
}
