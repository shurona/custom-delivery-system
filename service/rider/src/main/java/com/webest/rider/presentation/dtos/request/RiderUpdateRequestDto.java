package com.webest.rider.presentation.dtos.request;

import com.webest.rider.domain.model.RiderTransportation;
import jakarta.validation.constraints.NotNull;

public record RiderUpdateRequestDto(
    @NotNull(message = "배송수단 정보를 입력해주세요")
    RiderTransportation transportation
) {

}
