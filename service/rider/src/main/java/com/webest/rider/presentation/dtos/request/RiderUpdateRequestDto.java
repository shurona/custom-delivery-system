package com.webest.rider.presentation.dtos.request;

import com.webest.rider.domain.model.RiderTransportation;

public record RiderUpdateRequestDto(
    RiderTransportation transportation
) {

}
