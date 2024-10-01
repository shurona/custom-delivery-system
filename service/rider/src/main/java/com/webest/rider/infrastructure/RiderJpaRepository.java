package com.webest.rider.infrastructure;

import com.webest.rider.domain.model.Rider;
import com.webest.rider.domain.model.vo.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderJpaRepository extends JpaRepository<Rider, Long> {

    long countByPhone(PhoneNumber phoneNumber);

}
