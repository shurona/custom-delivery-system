package com.webest.store.store.domain.model;

import com.webest.app.address.service.AddressDto;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StoreAddress {
    private String city;                    // 시
    private String street;                  // 구
    private String district;                // 동
    private String detailAddress;           // 상세 주소
    private Long addressCode;            // 주소 코드 번호

    public static StoreAddress from(AddressDto dto, String detailAddress) {
        return new StoreAddress(
                dto.city(),          // 시
                dto.street(),        // 구
                dto.district(),      // 동
                detailAddress,       // 상세 주소는 따로 받아서 사용
                dto.code()           // 주소 코드
        );
    }
}
