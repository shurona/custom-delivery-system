package com.webest.store.store.infrastructure.naver;

import com.webest.store.store.infrastructure.naver.dto.GeoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.webest.store.store.infrastructure.naver.NaverGeoClient.NAVER_GEO_API;
import static com.webest.store.store.infrastructure.naver.NaverGeoClient.NAVER_GEO_API_URL;

@FeignClient(
        name = NAVER_GEO_API,
        url = NAVER_GEO_API_URL,
        configuration = NaverGeoClientHeaderConfig.class
)
public interface NaverGeoClient {
    String NAVER_GEO_API = "naver-openapi";
    String NAVER_GEO_API_URL = "https://naveropenapi.apigw.ntruss.com";

    @GetMapping(path = "/map-geocode/v2/geocode")
    GeoResponse getCoordinatesFromAddress(@RequestParam("query") String address);
}
