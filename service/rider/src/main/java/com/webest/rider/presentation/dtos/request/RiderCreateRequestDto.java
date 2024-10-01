package com.webest.rider.presentation.dtos.request;

import com.webest.rider.domain.model.RiderTransportation;

public record RiderCreateRequestDto(
    String userId,
    String password,
    String phoneNumber,
    RiderTransportation transportation
) {

}
