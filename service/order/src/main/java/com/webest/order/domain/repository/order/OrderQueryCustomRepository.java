package com.webest.order.domain.repository.order;

import com.webest.order.application.dtos.OrderSearchDto;
import com.webest.order.infrastructure.model.OrderQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderQueryCustomRepository {
    Page<OrderQuery> searchOrders(OrderSearchDto request, Pageable pageable);
}
