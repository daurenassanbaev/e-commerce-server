package com.ecommerce.productservice.product.model.converter;

import com.ecommerce.common.model.dto.ProductPriceDto;
import com.ecommerce.productservice.product.model.entity.Product;

public class ProductPriceConverter {

    public static ProductPriceDto toDto(Product product) {
        ProductPriceDto dto = new ProductPriceDto();
        dto.setProductId(product.getId());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
