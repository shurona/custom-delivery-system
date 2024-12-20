package com.webest.store.store.application;

import com.webest.app.address.csv.ReadAddressCsv;
import com.webest.app.address.service.AddressDto;
import com.webest.store.store.domain.model.Store;
import com.webest.store.store.domain.model.StoreAddress;
import com.webest.store.store.domain.repository.CustomStoreRepository;
import com.webest.store.store.domain.repository.StoreRepository;
import com.webest.store.store.exception.StoreErrorCode;
import com.webest.store.store.exception.StoreException;
import com.webest.store.store.infrastructure.naver.NaverGeoClient;
import com.webest.store.store.infrastructure.naver.dto.GeoResponse;
import com.webest.store.store.infrastructure.naver.dto.NaverAddress;
import com.webest.store.store.infrastructure.user.UserClient;
import com.webest.store.store.infrastructure.user.dto.UserResponse;
import com.webest.store.store.presentation.dto.Coordinates;
import com.webest.store.store.presentation.dto.CreateStoreRequest;
import com.webest.store.store.presentation.dto.DeliveryAreaRequest;
import com.webest.store.store.presentation.dto.StoreResponse;
import com.webest.store.store.presentation.dto.UpdateStoreAddressRequest;
import com.webest.web.common.UserRole;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final CustomStoreRepository customStoreRepository;
    private final NaverGeoClient naverGeoClient;
    private final UserClient userClient;
    private final ReadAddressCsv readAddressCsv;

    private final StoreCacheService storeCacheService;
    private final GeoOperation geoOperation;

    // 가게 생성
    // 가게 주소, 위경도는 생성 시 설정하지 않고 따로 업데이트
    @Transactional
    public StoreResponse saveStore(CreateStoreRequest request, String userId, UserRole role) {

        if (role != UserRole.MASTER && role != UserRole.OWNER) {
            throw new StoreException(StoreErrorCode.UNAUTHORIZED_ACCESS);
        }

        Store store = request.toEntity(userId);
        storeRepository.save(store);
        return updateStoreCache(store);
    }

    // 가게 주소 등록
    @Transactional
    public StoreResponse updateStoreAddress(UpdateStoreAddressRequest request, String userId,
        UserRole role) {
        Store store = findStoreById(request.storeId());

        // 권한 검증
        validatePermission(store, userId, role);

        // 주소로 법정동 코드 받아오기
        AddressDto addressDto = readAddressCsv.findAddressByDistrict(request.city(),
            request.street(), request.district());
        StoreAddress storeAddress = StoreAddress.from(addressDto, request.detailAddress());

        // 주소 조합 (시, 구, 동, 상세주소)
        String fullAddress =
            request.city() + " " + request.street() + " " + request.district() + " "
                + request.detailAddress();

        // 주소로 위경도 받아오기
        GeoResponse geoResponse = fetchGeoCoordinates(fullAddress);
        NaverAddress addressInfo = geoResponse.getAddresses().get(0);

        // 위경도 파싱
        Double latitude = Double.parseDouble(addressInfo.getY()); // 위도
        Double longitude = Double.parseDouble(addressInfo.getX()); // 경도

        // 가게 정보 업데이트
        store.updateAddress(storeAddress, latitude, longitude);

        // 가게 위치 Geo 업데이트
        updateLocationToGeoCache(store);

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

        return updateStoreCache(store);
    }

    // 가게 단건 조회
    public StoreResponse getStoreById(Long id) {
        StoreResponse storeResponse = storeCacheService.getCachedStore(id);
        if (storeResponse == null) {
            Store store = findStoreById(id);
            storeResponse = updateStoreCache(store);
        }
        return storeResponse;
    }

    // 가게 법정동별 조회
    public List<StoreResponse> getStoresByUserAddressCode(String userId) {
        UserResponse userResponse = userClient.getUser(userId).getData();
        return findStoresByAddressCode(userResponse.addressCode()).stream().map(StoreResponse::of)
            .toList();
    }

    // 유저 근처 상점 조회
    public List<StoreResponse> getStoresByCoordinates(Coordinates coordinates) {
        List<String> storeIds = geoOperation.findNearByStores(coordinates.longitude(),
            coordinates.latitude(), coordinates.radius());
        return storeIds.stream()
            .map(storeId -> storeRepository.findById(Long.parseLong(storeId))
                .map(StoreResponse::of)  // Store 객체를 StoreResponse로 변환
                .orElse(null))  // 없으면 null 반환
            .filter(Objects::nonNull)  // null 제거
            .collect(Collectors.toList());
    }

    // 가게 삭제
    @Transactional
    public void deleteStore(Long storeId, String userId, UserRole role) {
        Store store = findStoreById(storeId);
        // 권한 검증
        validatePermission(store, userId, role);

        storeRepository.delete(store);
        storeCacheService.deleteStoreCache(store.getId());
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
        storeCacheService.cacheStore(store.getId(), storeResponse);
        return storeResponse;
    }

    // 가게 위치 Geo 업데이트 메서드
    private void updateLocationToGeoCache(Store store) {
        geoOperation.add(store.getLongitude(), store.getLatitude(), String.valueOf(store.getId()));
    }

    // 권한 검증 메서드
    private void validatePermission(Store store, String userId, UserRole role) {
        if (role == UserRole.OWNER) {
            // OWNER일 경우 가게 주인 여부 확인
            if (!store.getOwnerId().equals(userId)) {
                throw new StoreException(StoreErrorCode.UNAUTHORIZED_ACCESS);  // 권한 없는 경우 예외 처리
            }
        } else if (role == UserRole.USER) {
            throw new StoreException(StoreErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
}
