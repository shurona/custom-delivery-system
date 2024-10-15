package com.webest.rider.domain.repository;

import com.webest.rider.domain.model.Rider;
import com.webest.rider.domain.model.RiderSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RiderQueryRepository {

    Page<Rider> findRiderListByQuery(Pageable pageable, RiderSearchCondition searchCondition);

}
