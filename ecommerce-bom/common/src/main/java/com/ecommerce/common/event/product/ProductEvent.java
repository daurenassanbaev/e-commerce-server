package com.ecommerce.common.event.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductEvent {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private boolean isActive;
    private Map<String, Object> attributes;
}

