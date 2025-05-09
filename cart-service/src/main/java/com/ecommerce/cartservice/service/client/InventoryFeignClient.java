package com.ecommerce.cartservice.service.client;

import com.ecommerce.common.model.dto.response.InventoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="inventory-service")
public interface InventoryFeignClient {

    @GetMapping("/api/inventory/{productId}")
    InventoryResponseDto getInventory(@PathVariable("productId") Long productId);
}
