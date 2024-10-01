package com.webest.store.store.infra.naver;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;


public class NaverRequestInterceptor implements RequestInterceptor {

    private static final String API_KEY_ID = "x-ncp-apigw-api-key-id";
    private static final String API_KEY = "x-ncp-apigw-api-key";
    private static final String ACCEPT = "Accept";

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    // 요청 헤더 설정
    @Override
    public void apply(RequestTemplate template) {
        template.header(API_KEY_ID, clientId);
        template.header(API_KEY, clientSecret);
        template.header(ACCEPT, "application/json");
    }
}
