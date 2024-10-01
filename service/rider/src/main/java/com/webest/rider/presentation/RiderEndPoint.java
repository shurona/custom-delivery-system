package com.webest.rider.presentation;


import com.webest.rider.presentation.dtos.request.RiderLoginRequestDto;
import com.webest.rider.presentation.dtos.response.RiderResponseDto;
import com.webest.web.response.CommonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/riders")
public class RiderEndPoint {

    @PostMapping("sign-in")
    public CommonResponse<RiderResponseDto> dkd(
        @RequestBody RiderLoginRequestDto requestDto
    ) {

        return CommonResponse.success(null);
    }


}
