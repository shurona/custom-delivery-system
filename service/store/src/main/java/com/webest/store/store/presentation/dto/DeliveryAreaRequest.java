package com.webest.store.store.presentation.dto;

import java.util.List;

public record DeliveryAreaRequest(
        List<Long> addressCodeList
) {
}
