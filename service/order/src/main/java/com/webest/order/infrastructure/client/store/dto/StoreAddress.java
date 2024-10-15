package com.webest.order.infrastructure.client.store.dto;

public record StoreAddress(String city,
                           String street,
                           String district,
                           String detailAddress,
                           Long addressCode) {


}
