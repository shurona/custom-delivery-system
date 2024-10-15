package com.webest.store.store.infrastructure.naver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Meta {
    private int totalCount; // 응답 결과 개수
    private int page; // 현재 페이지 번호
    private int count; // 페이지 내 결과 개수
}
