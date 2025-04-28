package com.ecommerce.productservice.product.model.converter;

import com.ecommerce.productservice.product.model.dto.ProductDto;
import com.ecommerce.productservice.product.model.entity.Product;

public class ProductConverter {

    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategoryId());
        dto.setAttributes(product.getAttributes());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        return dto;
    }
}