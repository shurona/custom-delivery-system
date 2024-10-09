package com.webest.store.store.infrastructure.naver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GeoResponse {

    private String status; // 응답 코드
    private Meta meta; // 메타 데이터
    private List<NaverAddress> addresses; // 주소 정보 목록
    private String errorMessage; // 오류 메시지 (500 오류 시에만 표시)
}
