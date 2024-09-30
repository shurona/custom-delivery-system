package com.webest.app.address.csv;

import static com.webest.web.common.CommonStaticVariable.ADDRESS_CSV;
import static com.webest.web.exception.ErrorCode.SERVER_ERROR;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.webest.app.address.service.AddressDto;
import com.webest.web.exception.ApplicationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * Address csv 주소 https://www.data.go.kr/data/15063424/fileData.do
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ReadAddressCsv {

    // [0] 코드
    // [1] 시도
    // [2] 시군구
    // [3] 읍면동
    private final Integer CODE_INDEX = 0;
    private final Integer CITY_INDEX = 1;
    private final Integer STREET_INDEX = 2;
    private final Integer DISTRICT_INDEX = 3;

    private final ResourceLoader resourceLoader;
    private Map<Long, String[]> addressSetInfo = new HashMap<>();

    private void checkAddressExist() {
        if (addressSetInfo.isEmpty()) {
            setAddressInfoFromCSV();
        }
    }

    /**
     * 메모리로 Address csv 파일 구성
     */
    private void setAddressInfoFromCSV() {
        // resource 갖고 온다.
        Resource resource = resourceLoader.getResource("classpath:" + ADDRESS_CSV);

        try {
            CSVReader reader = new CSVReader(
                new InputStreamReader(resource.getInputStream(), Charset.forName("EUC-KR")));

            List<String[]> strings = reader.readAll();

            int cursor = 0;
            for (String[] addressSet : strings) {
                if (cursor == 0) {
                    cursor++;
                    continue;
                }
                Long code = Long.valueOf(addressSet[CODE_INDEX]);
                addressSetInfo.put(code, addressSet);
            }

        } catch (IOException | CsvException e) {
            log.error("csv 읽는 중 에러 : ", e);
            throw new ApplicationException(SERVER_ERROR.getStatus(), SERVER_ERROR.getMessage());
        }

    }

    /**
     * 도시 목록 조회
     */
    public List<String> findCityList() {
        return List.of(StreetAddressConst.cityList);
    }

    /**
     * 도시에 속한 시/군/구 조회
     */
    public List<String> findStreetListByCity(String city) {
        checkAddressExist();
        return this.addressSetInfo.values().stream().filter(
                addressSet -> addressSet[CITY_INDEX].equals(city)
                    && StringUtils.hasText(addressSet[STREET_INDEX]))
            .map(addressSet -> addressSet[STREET_INDEX]).distinct().toList();
    }

    /**
     * city 기준으로 주소 목록 조회
     */
    public List<AddressDto> findAddressListByCity(String city) {
        checkAddressExist();
        return this.addressSetInfo.values().stream()
            .filter(addressSet -> addressSet[CITY_INDEX].equals(city))
            .map(AddressDto::from).toList();
    }

    /**
     * street 기준으로 주소 조회
     */
    public List<AddressDto> findAddressByStreet(String city, String street) {
        checkAddressExist();
        return this.addressSetInfo.values().stream()
            .filter(addressSet -> (addressSet[CITY_INDEX].equals(city)
                && addressSet[STREET_INDEX].equals(street)
                && StringUtils.hasText(addressSet[DISTRICT_INDEX])))
            .map(AddressDto::from).toList();
    }

    /**
     * City, Street, District 기준으로 주소 단일 조회
     */
    public AddressDto findAddressByDistrict(String city, String street, String district) {
        checkAddressExist();
        return this.addressSetInfo.values().stream()
            .filter(addressSet -> (addressSet[CITY_INDEX].equals(city)
                && addressSet[STREET_INDEX].equals(street)
                && addressSet[DISTRICT_INDEX].equals(district)))
            .map(AddressDto::from).findFirst().orElse(null);
    }

    /**
     * 코드로 주소 조회
     */
    public AddressDto findAddressByCode(Long code) {
        checkAddressExist();
        return Optional.ofNullable(this.addressSetInfo.get(code))
            .map(AddressDto::from)
            .orElse(null);
    }

}
