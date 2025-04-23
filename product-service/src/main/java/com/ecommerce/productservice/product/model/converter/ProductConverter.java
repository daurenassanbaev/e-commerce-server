package com.ecommerce.productservice.product.model.converter;

import com.ecommerce.productservice.product.model.dto.ProductDto;
import com.ecommerce.productservice.product.model.entity.Product;

public class ProductConverter {

    public static ProductDto entityToDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategoryId());
        dto.setAttributes(product.getAttributes());

        return dto;
    }
}