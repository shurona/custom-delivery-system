package com.webest.user.domain.model;

import com.webest.app.jpa.BaseEntity;
import com.webest.user.domain.model.vo.UserDto;
import com.webest.user.presentation.dto.request.UserUpdateRequest;
import com.webest.web.common.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SQLRestriction("is_deleted = false")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column( nullable = false, length = 50)
    private String userName;
    @Column( nullable = false, length = 50, unique = true)
    private String email;
    @Column( nullable = false, length = 50)
    private String phone;
    @Column(nullable = false)
    private UserRole role;
    private Long addressCode;            // 주소 코드 번호
    private String city;                    // 시
    private String street;                  // 구
    private String district;                // 동
    private String detailAddress;           // 상세 주소

    private double latitude;            // 위도
    private double longitude;           // 경도
    // Auth -> Dto
    public UserDto to(){
        return new UserDto(
                this.userId,
                this.password,
                this.userName,
                this.email,
                this.phone,
                this.role,
                this.addressCode,
                this.city,
                this.street,
                this.district,
                this.detailAddress,
                this.latitude,
                this.longitude
        );
    }

    // Dto -> Auth
    public static User from(UserDto dto){
        return new User(
                null,
                dto.userId(),
                dto.password(),
                dto.userName(),
                dto.email(),
                dto.phone(),
                dto.role(),
                dto.addressCode(),
                dto.city(),
                dto.street(),
                dto.district(),
                dto.detailAddress(),
                dto.latitude(),
                dto.longitude()
        );
    }

    public void update(UserUpdateRequest request, Long addressCode,double latitude,double longitude){
        this.password = request.password() !=null ? request.password() : this.password;
        this.userName = request.userName() !=null ? request.userName() : this.userName;
        this.email = request.email() !=null ? request.email() : this.email;
        this.phone = request.phone() !=null ? request.phone() : this.phone;
        this.addressCode = addressCode !=null ? addressCode : this.addressCode;
        this.city = request.city() !=null ? request.city() : this.city;
        this.street = request.street() !=null ? request.street() : this.street;
        this.district = request.district() !=null ? request.district() : this.district;
        this.detailAddress = request.detailAddress() !=null ? request.detailAddress() : this.detailAddress;
        this.latitude = latitude != 0.0 ? latitude : this.latitude;
        this.longitude = longitude != 0.0 ? longitude : this.longitude;

    }
}