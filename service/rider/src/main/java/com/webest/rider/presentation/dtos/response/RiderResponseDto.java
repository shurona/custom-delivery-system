package com.webest.rider.presentation.dtos.response;

import com.webest.rider.domain.model.Rider;
import com.webest.rider.domain.model.RiderTransportation;
import java.time.LocalDateTime;
import java.util.List;

public record RiderResponseDto(
    Long riderId,
    String userId,
    List<Long> addressCodeList,
    RiderTransportation transportation,
    LocalDateTime createdAt
) {

    public static RiderResponseDto from(Rider rider) {
        return new RiderResponseDto(rider.getRiderId(), rider.getUserId(),
            rider.getAddressCodeList(), rider.getTransportation(), rider.getCreatedAt());
    }

}
