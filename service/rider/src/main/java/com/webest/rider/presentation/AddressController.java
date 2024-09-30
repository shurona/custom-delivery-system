package com.webest.rider.presentation;

import com.webest.app.address.service.AddressDto;
import com.webest.app.address.service.AddressService;
import com.webest.web.response.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/riders")
@RestController
public class AddressController {

    private final AddressService addressService;

    /**
     * 코드로 주소 상세 조회
     */
    @GetMapping("/code/{code}")
    public CommonResponse<AddressDto> findAddressByCode(
        @PathVariable("code") Long code
    ) {

        return CommonResponse.success(addressService.findAddressByCode(code));
    }

    @GetMapping("/city")
    public CommonResponse<List<String>> findCityList() {

        return CommonResponse.success(addressService.findCityList());
    }

    @GetMapping("/city/{city}/street")
    public CommonResponse<List<String>> findStreetByCity(
        @PathVariable("city") String city
    ) {

        return CommonResponse.success(addressService.findStreetListByCity(city));
    }

    @GetMapping("city/{city}/street/{street}")
    public CommonResponse<List<AddressDto>> findAddressByStreet(
        @PathVariable("city") String city,
        @PathVariable("street") String street
    ) {
        List<AddressDto> addressListByStreet = addressService.findAddressListByStreet(city, street);
        return CommonResponse.success(addressListByStreet);
    }

}
