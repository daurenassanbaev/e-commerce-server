package com.ecommerce.orderservice.order.service.client;

import com.ecommerce.common.model.dto.ProductPriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @PostMapping("/api/products/internal/prices")
    List<ProductPriceDto> getPrices(List<Long> productIds);
}
