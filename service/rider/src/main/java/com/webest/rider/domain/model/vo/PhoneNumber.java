package com.webest.rider.domain.model.vo;

import com.webest.rider.common.exception.RiderErrorCode;
import com.webest.rider.common.exception.RiderException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PhoneNumber {

    public final static String PHONE_NUMBER_PATTERN = "^010-\\d{3,4}-\\d{4}$";

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    public PhoneNumber(String phoneNumber) {

        // Check Valid PhoneNumber
        if (!StringUtils.hasText(phoneNumber) || !Pattern.matches(PHONE_NUMBER_PATTERN,
            phoneNumber)) {
            throw new RiderException(RiderErrorCode.INVALID_PHONE_NUMBER_INPUT);
        }

        this.phoneNumber = phoneNumber;
    }
}
