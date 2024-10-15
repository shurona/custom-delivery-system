package com.webest.auth.presentation.dto.request;

import com.webest.web.common.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record UserJoinRequest(
        @NotNull(message = "UserId cannot be null")
        String userId,

        @NotNull(message = "Password cannot be null")
        @Size(min = 2, message = "Password not be less than two characters")
        String password,

        @NotNull(message = "Name cannot be null")
        @Size(min = 2, message = "Name not be less than two characters")
        String userName,

        @NotNull(message = "email cannot be null")
        @Size(min =2, message = "Email not be less than two characters")
        @Email
        String email,

        @NotNull(message = "phoneNumber cannot be null")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxxx-xxxx")
        String phone,

        UserRole role,

        @NotNull(message = "city cannot be null")
        String city,

        @NotNull(message = "street cannot be null")
        String street,

        @NotNull(message = "district cannot be null")
        String district,

        @NotNull(message = "district cannot be null")
        String detailAddress
) {


}
