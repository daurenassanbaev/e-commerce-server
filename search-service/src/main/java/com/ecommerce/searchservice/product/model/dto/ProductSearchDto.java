package com.ecommerce.searchservice.product.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductSearchDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Map<String, Object> attributes;
}
