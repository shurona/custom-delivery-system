package com.webest.store.store.application;

import com.webest.app.address.csv.ReadAddressCsv;
import com.webest.app.address.service.AddressDto;
import com.webest.store.store.domain.model.StoreAddress;
import com.webest.store.store.domain.repository.CustomStoreRepository;
import com.webest.store.store.presentation.dto.CreateStoreRequest;
import com.webest.store.store.presentation.dto.DeliveryAreaRequest;
import com.webest.store.store.presentation.dto.StoreResponse;
import com.webest.store.store.presentation.dto.UpdateStoreAddressRequest;
import com.webest.store.store.domain.model.Store;
import com.webest.store.store.domain.repository.StoreRepository;
import com.webest.store.store.exception.StoreErrorCode;
import com.webest.store.store.exception.StoreException;
import com.webest.store.store.infrastructure.naver.NaverGeoClient;
import com.webest.store.store.infrastructure.naver.dto.GeoResponse;
import com.webest.store.store.infrastructure.naver.dto.NaverAddress;
import com.webest.web.common.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final CustomStoreRepository customStoreRepository;
    private final NaverGeoClient naverGeoClient;
    private final ReadAddressCsv readAddressCsv;

    private final RedisTemplate<String, Object> storeRedisTemplate;
    private static final String STORE_CACHE_PREFIX = "store:";
    private static final Duration CACHE_EXPIRATION = Duration.ofMinutes(30); // 캐시 만료 시간 설정

    // 가게 생성
    // 가게 주소, 위경도, 배달 반경, 배달 팁은 생성 시 설정하지 않고 따로 업데이트
    @Transactional
    public StoreResponse saveStore(CreateStoreRequest request) {
        Store store = request.toEntity();
        storeRepository.save(store);

        // 생성 후 캐시 업데이트
        StoreResponse storeResponse = StoreResponse.of(store);
        storeRedisTemplate.opsForValue().set(STORE_CACHE_PREFIX + store.getId(), storeResponse, CACHE_EXPIRATION);

        return StoreResponse.of(store);
    }

    // 가게 주소 등록
    @Transactional
    public StoreResponse updateStoreAddress(UpdateStoreAddressRequest request) {
        Store store = findStoreById(request.storeId());

        // 주소로 법정동 코드 받아오기
        AddressDto addressDto = readAddressCsv.findAddressByDistrict(request.city(), request.street(), request.district());
        StoreAddress storeAddress = StoreAddress.from(addressDto, request.detailAddress());

        // 주소 조합 (시, 구, 동, 상세주소)
        String fullAddress = request.city() + " " + request.street() + " " + request.district() + " " + request.detailAddress();


        // 주소로 위경도 받아오기
        GeoResponse geoResponse = fetchGeoCoordinates(fullAddress);
        NaverAddress addressInfo = geoResponse.getAddresses().get(0);

        // 위경도 파싱
        Double latitude = Double.parseDouble(addressInfo.getY()); // 위도
        Double longitude = Double.parseDouble(addressInfo.getX()); // 경도

        // 가게 정보 업데이트
        store.updateAddress(storeAddress, latitude, longitude);

        // 캐시 업데이트
        return updateStoreCache(store);
    }

    // 배달 가능 범위 등록 (법정동)
    @Transactional
    public StoreResponse updateDeliveryArea(Long storeId, DeliveryAreaRequest request) {

        Store store = findStoreById(storeId);

        // Address code가 존재하는 지 확인
        request.addressCodeList().forEach(
                (code) -> {
                    if (readAddressCsv.findAddressByCode(code) == null) {
                        throw new StoreException(StoreErrorCode.INVALID_ADDRESS);
                    }
                }
        );

        store.registerDeliveryArea(request.addressCodeList());

        return StoreResponse.of(store);
    }



    // 가게 단건 조회
    public StoreResponse getStoreById(Long id) {
        StoreResponse storeResponse = (StoreResponse) storeRedisTemplate.opsForValue().get(STORE_CACHE_PREFIX + id);
        if (storeResponse == null) {
            Store store = findStoreById(id);
            storeResponse = StoreResponse.of(store);

            storeRedisTemplate.opsForValue().set(STORE_CACHE_PREFIX + store.getId(), storeResponse, CACHE_EXPIRATION);
        }

        return storeResponse;
    }

    // 가게 법정동별 조회
    public List<StoreResponse> getStoresByUser(Long addressCode) {
        return findStoresByAddressCode(addressCode).stream().map(StoreResponse::of).toList();
    }

    // 가게 전체 조회
    public Page<StoreResponse> getAllStores(Pageable pageable) {
        Page<Store> stores = storeRepository.findAll(pageable);
        return stores.map(StoreResponse::of);
    }


    // 가게 유저 역할별 조회
//    public List<StoreResponse> getStoresByUserRole(Long userId, UserRole role) {
//
//    }

    // 가게 삭제
    @Transactional
    public void deleteStore(Long id) {
        Store store = findStoreById(id);
        storeRepository.delete(store);
    }

    // ID로 상점을 찾는 공통 메서드
    private Store findStoreById(Long id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new StoreException(StoreErrorCode.STORE_NOT_FOUND)
        );
    }

    // 법정동별 상점 조회
    private List<Store> findStoresByAddressCode(Long addressCode) {
        return customStoreRepository.findStoresByAddressCode(addressCode);
    }

    // 주소로 위경도 가져오기
    private GeoResponse fetchGeoCoordinates(String address) {
        GeoResponse geoResponse = naverGeoClient.getCoordinatesFromAddress(address);
        if (geoResponse.getAddresses() == null || geoResponse.getAddresses().isEmpty()) {
            throw new StoreException(StoreErrorCode.INVALID_ADDRESS);
        }

        return geoResponse;
    }

    // 가게 캐시 업데이트 메서드
    private StoreResponse updateStoreCache(Store store) {
        StoreResponse storeResponse = StoreResponse.of(store);
        storeRedisTemplate.opsForValue().set(STORE_CACHE_PREFIX + store.getId(), storeResponse, CACHE_EXPIRATION);
        return storeResponse;
    }

}
