package com.webest.store.category.domain.repository;

import com.webest.store.category.domain.model.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<StoreCategory, Long> {
    boolean existsByCategoryKey(String key);
}
