package com.ecommerce.cartservice.service.client;

import com.ecommerce.common.model.dto.response.ProductStatusResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="product-service")
public interface ProductFeignClient {

    @GetMapping("/api/products/internal/{id}/status")
    ProductStatusResponseDto getProductStatus(@PathVariable("id") Long id);
}
