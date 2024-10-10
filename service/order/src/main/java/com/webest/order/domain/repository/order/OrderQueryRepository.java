package com.webest.order.domain.repository.order;

import com.webest.order.infrastructure.model.OrderQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderQueryRepository extends MongoRepository<OrderQuery, String>, OrderQueryCustomRepository{

    Optional<OrderQuery> findByOrderId(Long orderId);
}
