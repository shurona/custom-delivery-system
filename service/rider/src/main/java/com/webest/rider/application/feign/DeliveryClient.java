package com.webest.rider.application.feign;

import com.webest.rider.application.feign.dtos.DeliverySearchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "delivery-service"
)
public interface DeliveryClient {

    @GetMapping("/api/v1/deliveries/search")
    public void findDeliveryListByCode(@RequestBody DeliverySearchRequest request);

}
