package com.webest.store.store.domain.repository;

import com.webest.store.store.domain.model.Store;

import java.util.List;

public interface CustomStoreRepository {
    public List<Store> findStoresByAddressCode(Long addressCode);
    public List<Store> findStoresByAddressCodeAndCategory(Long categoryId, Long addressCode);
}
