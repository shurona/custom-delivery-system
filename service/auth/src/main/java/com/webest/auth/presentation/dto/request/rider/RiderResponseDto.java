package com.webest.auth.presentation.dto.request.rider;

import com.webest.auth.domain.model.RiderTransportation;
import java.time.LocalDateTime;
import java.util.List;

public record RiderResponseDto(
    Long riderId,
    String userId,
    List<Long> addressCodeList,
    RiderTransportation transportation,
    LocalDateTime createdAt
) {


}