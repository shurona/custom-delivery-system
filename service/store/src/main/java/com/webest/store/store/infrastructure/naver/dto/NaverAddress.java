package com.webest.store.store.infrastructure.naver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NaverAddress {
    private String roadAddress; // 도로명 주소
    private String jibunAddress; // 지번 주소
    private String englishAddress; // 영어 주소
    private List<AddressElement> addressElements; // 주소 구성 요소 정보
    private String x; // X 좌표(경도)
    private String y; // Y 좌표(위도)
    private double distance; // 중심 좌표로부터의 거리(m)
}
