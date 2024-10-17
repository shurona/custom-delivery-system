package com.webest.store.store.presentation.dto;

import com.webest.store.store.domain.model.Store;
import jakarta.validation.constraints.*;

import java.time.LocalTime;

public record CreateStoreRequest(
        @NotBlank(message = "상점 이름은 필수 입력 항목입니다.")
        String name,
        @NotNull(message = "카테고리 ID를 입력해주세요.")
        Long categoryId,
        @Min(value = 1, message = "준비 시간은 최소 1분 이상이어야 합니다.")
        Integer preparationTime,
        @NotNull(message = "최소 주문 금액을 입력해주세요.")
        @DecimalMin(value = "0.0", inclusive = false, message = "최소 주문 금액은 0보다 커야 합니다.")
        Double minimumOrderAmount,
        @NotBlank(message = "전화번호를 입력해주세요.")
        @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "잘못된 전화번호 형식입니다.")
        String phone,
        LocalTime openTime,
        LocalTime closeTime,
        @NotNull(message = "배달 팁은 필수 입력 항목입니다.")
        @DecimalMin(value = "0.0", message = "배달 팁은 최소 0원이어야 합니다.")
        Double deliveryTip
) {
    public Store toEntity(String ownerId) {
        return Store.of(name, ownerId, categoryId, preparationTime, minimumOrderAmount, phone, openTime, closeTime, deliveryTip);
    }
}

