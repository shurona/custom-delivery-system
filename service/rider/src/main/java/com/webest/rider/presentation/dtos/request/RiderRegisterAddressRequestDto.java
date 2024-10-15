package com.webest.rider.presentation.dtos.request;

import java.util.List;

public record RiderRegisterAddressRequestDto(
    List<Long> addressCodeList
) {

}
