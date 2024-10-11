package com.webest.order.domain.repository.orderproduct;

import com.webest.order.domain.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductCustomRepository{
}
