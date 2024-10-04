package com.webest.delivery.domain.repository;

import com.webest.delivery.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>, DeliveryCustomRepository {
    Optional<Delivery> findByOrderId(Long orderId);
}

