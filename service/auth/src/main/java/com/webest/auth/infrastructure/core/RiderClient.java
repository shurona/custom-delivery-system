package com.webest.auth.infrastructure.core;

import com.webest.auth.presentation.dto.request.RiderCreateRequestDto;
import com.webest.auth.presentation.dto.request.UserJoinRequest;
import com.webest.auth.presentation.dto.response.JoinResponse;
import com.webest.web.response.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "rider-service")
public interface RiderClient {

    @PostMapping("/api/v1/riders")
    Long createRider(@Validated @RequestBody RiderCreateRequestDto requestDto);
}