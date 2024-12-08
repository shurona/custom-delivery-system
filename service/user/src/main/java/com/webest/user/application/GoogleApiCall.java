package com.webest.user.application;

import com.webest.user.infrastructure.geocode.GoogleMapResponse;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GoogleApiCall {

    @Value("${google.map.key}")
    private Object API_KEY;// 실제 서버에서 구동할때는 무조건 환경변수에 숨겨야함 절대 노출되면 안됨!!!


    // 위/경도 구하는 중복 코드 분리
    public double[] getGeocode(String address) {
        double[] result = new double[2];

        UriComponents uri = UriComponentsBuilder.newInstance()          // UriComponentsBuilder.newInstance = uri 주소를 직접 조립하여 만든다
            // https://maps.googleapis.com/maps/api/geocode/json?address="address"&key="API_KEY"와 같음
            // 위 처럼 한번에 사용하지 않고 조립해서 사용하는 이유는 address나 key값처럼 외부에서 값을 받아올때 쉽게 넣어 조립이 가능하기 때문
            .scheme("https")
            .host("maps.googleapis.com")
            .path("/maps/api/geocode/json")
            .queryParam("key", API_KEY)
            .queryParam("address", address)
            .build();

        GoogleMapResponse response = new RestTemplate().getForEntity(uri.toUriString(),
                GoogleMapResponse.class)
            .getBody();     // 구글 map api에서 반환해주는 json형식을 MapResponse클래스 형식에 맞춰 받아옴
        result[0] = Arrays.stream(response.getResult()).findFirst().get().getGeometry()
            .getLocation().getLat();
        result[1] = Arrays.stream(response.getResult()).findFirst().get().getGeometry()
            .getLocation().getLng();

        return result;
    }

}
