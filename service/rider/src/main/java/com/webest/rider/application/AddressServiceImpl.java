package com.webest.rider.application;

import com.webest.app.address.csv.ReadAddressCsv;
import com.webest.app.address.service.AddressDto;
import com.webest.app.address.service.AddressService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final ReadAddressCsv readAddressCsv;

    @Override
    public List<String> findCityList() {
        return readAddressCsv.findCityList();
    }

    @Override
    public List<String> findStreetListByCity(String city) {
        return readAddressCsv.findStreetListByCity(city);
    }

    @Override
    public List<AddressDto> findAddressListByStreet(String city, String street) {
        return readAddressCsv.findAddressByStreet(city, street);
    }

    @Override
    public AddressDto findAddressByDistrict(String city, String street, String district) {
        return readAddressCsv.findAddressByDistrict(city, street, district);
    }

    @Override
    public AddressDto findAddressByCode(Long code) {
        return readAddressCsv.findAddressByCode(code);
    }
}
