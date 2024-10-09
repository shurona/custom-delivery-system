package com.webest.store.store.infrastructure.naver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AddressElement {
    private List<String> type; // 주소 구성 요소 타입
    private String longName; // 주소 구성 요소 이름
    private String shortName; // 주소 구성 요소 축약 이름
    private String code; // 코드 (필요할 경우)
}
