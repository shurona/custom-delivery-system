package com.webest.store.store.domain;

import com.webest.app.jpa.BaseEntity;
import com.webest.store.product.domain.Product;
import com.webest.store.store.domain.converter.StoreAddressConverter;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "p_store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE p_store SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name; // 가게 이름

    private Long ownerId; // 가게 주인 ID

    private Long categoryId; // 카테고리 ID, FK

    private Integer preparationTime; // 조리 시간 (분 단위)

    private Double minimumOrderAmount; // 최소 주문 금액

    @Enumerated(EnumType.STRING)
    private StoreStatus status = StoreStatus.CLOSED; // 가게 상태 : OPEN, PREPARING, CLOSED

    private String phone; // 전화 번호

    private LocalTime openTime; // 오픈 시간

    private LocalTime closeTime; // 클로즈 시간

    private String address; // 주소

    private Double latitude; // 위도

    private Double longitude; // 경도

    private Double deliveryRadius; // 배달 반경

    private Double deliveryTip; // 배달팁

    @Column(name = "address_codes")
    @Convert(converter = StoreAddressConverter.class)
    private List<Long> addressCodeList = new ArrayList<>();

    public static Store of(String name, Long ownerId, Long categoryId, Integer preparationTime, Double minimumOrderAmount, String phone, LocalTime openTime, LocalTime closeTime) {
        Store store = new Store();
        store.name = name;
        store.ownerId = ownerId;
        store.categoryId = categoryId;
        store.preparationTime = preparationTime;
        store.minimumOrderAmount = minimumOrderAmount;
        store.status = StoreStatus.CLOSED; // status는 항상 CLOSED로 고정
        store.phone = phone;
        store.openTime = openTime;
        store.closeTime = closeTime;
        return store;
    }

    // 주소, 위도, 경도 업데이트 메서드
    public void updateAddress(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void registerDeliveryArea(List<Long> addressCodes) {
        this.addressCodeList = addressCodes;
    }
}
