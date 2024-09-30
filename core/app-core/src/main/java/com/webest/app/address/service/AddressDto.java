package com.webest.app.address.service;

public record AddressDto(
    Long code,
    String city,
    String street,
    String district
) {

    public static AddressDto from(String[] arr) {
        return new AddressDto(Long.valueOf(arr[0]), arr[1], arr[2], arr[3]);
    }

}
