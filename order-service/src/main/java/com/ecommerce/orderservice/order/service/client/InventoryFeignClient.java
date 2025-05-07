package com.ecommerce.orderservice.order.service.client;

import com.ecommerce.common.model.dto.request.ReleaseRequestDto;
import com.ecommerce.common.model.dto.request.ReserveRequestDto;
import com.ecommerce.common.model.dto.response.ReleaseResponseDto;
import com.ecommerce.common.model.dto.response.ReserveResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service")
public interface InventoryFeignClient {

    @PostMapping("/api/inventory/internal/{productId}/reserve")
    ReserveResponseDto reserve(@PathVariable Long productId, @RequestBody ReserveRequestDto request);

    @PostMapping("/api/inventory/internal/{productId}/release")
    ReleaseResponseDto release(@PathVariable Long productId, @RequestBody ReleaseRequestDto releaseRequestDto);
}
