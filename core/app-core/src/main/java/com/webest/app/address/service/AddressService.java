package com.webest.app.address.service;

import java.util.ArrayList;
import java.util.List;

public interface AddressService {

    /**
     * 주소 목록 조회
     */
    public List<String> findCityList();

    /**
     * 시/도에 속한 시/군/구 목록 조회
     */
    public List<String> findStreetListByCity(String city);

    /**
     * 시/도에 속한 주소 목록 조회
     */
    default public List<AddressDto> findAddressListByCity(String city) {
        return new ArrayList<>();
    }

    /**
     * 시/군/구에 속한 주소 목록 조회
     */
    default public List<AddressDto> findAddressListByStreet(String city, String street) {
        return new ArrayList<>();
    }

    /**
     * 구역을 기준으로 주소 조회
     */
    default public AddressDto findAddressByDistrict(String city, String street, String district) {
        return null;
    }

    /**
     * 주소를 코드로 조회
     */
    default public AddressDto findAddressByCode(Long code) {
        return null;
    }
}
