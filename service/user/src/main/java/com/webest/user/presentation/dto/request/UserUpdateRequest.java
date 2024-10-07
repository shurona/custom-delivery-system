package com.webest.user.presentation.dto.request;

import com.webest.web.common.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min = 2, message = "Password not be less than two characters")
        String password,

        @Size(min = 2, message = "Name not be less than two characters")
        String userName,

        @Size(min =2, message = "Email not be less than two characters")
        @Email
        String email,

        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxxx-xxxx")
        String phone,

        String city,

        String street,

        String district
) {
}
