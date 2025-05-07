package com.ecommerce.cartservice.service.client;

import com.ecommerce.common.model.dto.request.OrderRequestDto;
import com.ecommerce.common.model.dto.response.OrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="order-service")
public interface OrderFeignClient {

    @PostMapping("/api/orders/internal")
    OrderResponseDto create(@RequestBody OrderRequestDto orderRequestDto);
}
