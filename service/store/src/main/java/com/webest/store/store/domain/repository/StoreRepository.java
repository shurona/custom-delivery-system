package com.webest.store.store.domain.repository;

import com.webest.store.store.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
