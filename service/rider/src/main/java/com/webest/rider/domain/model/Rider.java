package com.webest.rider.domain.model;

import com.webest.app.jpa.BaseEntity;
import com.webest.rider.domain.model.converter.RiderAddressConverter;
import com.webest.rider.domain.model.vo.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction(BaseEntity.DEFAULT_CONDITION)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "p_rider")
public class Rider extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long riderId;

    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(name = "address_codes", length = 1000)
    @Convert(converter = RiderAddressConverter.class)
    private List<Long> addressCodeList = new ArrayList<>();

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Embedded
    private PhoneNumber phone;

    @Column // TODO: ENUM 적용
    private String status;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RiderTransportation transportation;

    public static Rider createRider(String userId, String password, String phone,
        RiderTransportation transportation) {
        Rider rider = new Rider();
        rider.userId = userId;
        rider.password = password;
        rider.phone = new PhoneNumber(phone);
        rider.transportation = transportation;
        return rider;
    }

    /**
     * 라이더 배달 가능한 주소 등록
     */
    public void registerAddress(List<Long> addressCodes) {
        this.addressCodeList = addressCodes;
    }

    /**
     * 라이더 정보 업데이트
     */
    public void updateRiderInfo(RiderTransportation transportation) {
        this.transportation = transportation;
    }

    /**
     * 라이더 삭제
     */
    public void deleteRider() {
        this.isDeleted = true;
    }
}
