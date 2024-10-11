package com.webest.delivery.domain.repository;

import com.webest.delivery.domain.model.DeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long>, DeliveryRecordCustomRepository {
}
