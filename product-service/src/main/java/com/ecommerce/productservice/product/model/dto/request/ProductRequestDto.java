package com.ecommerce.productservice.product.model.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Map<String, Object> attributes;
}