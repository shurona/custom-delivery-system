package com.webest.auth.domain.model;

import com.webest.app.jpa.BaseEntity;
import com.webest.auth.domain.model.vo.AuthDto;
import com.webest.web.common.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SQLRestriction("is_deleted = false")
public class Auth extends BaseEntity {
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


    // Dto -> Auth
    public static Auth from(AuthDto dto){
        return new Auth(
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
                dto.district()
        );
    }

    // Auth -> Dto
    public AuthDto to(){
        return new AuthDto(
                this.id,
                this.userId,
                this.password,
                this.userName,
                this.email,
                this.phone,
                this.role,
                this.addressCode,
                this.city,
                this.street,
                this.district
        );
    }
}