package com.webest.store.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<StoreCategory, Long> {
    boolean existsByCategoryKey(String key);
}
