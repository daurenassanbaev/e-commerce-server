package com.ecommerce.searchservice.product.model.converter;

import com.ecommerce.common.model.event.product.ProductEvent;
import com.ecommerce.searchservice.product.model.document.ProductDocument;
import com.ecommerce.searchservice.product.model.dto.ProductSearchDto;

import java.util.Map;

public class ProductSearchConverter {

    public static ProductSearchDto toDto(ProductDocument doc) {
        ProductSearchDto dto = new ProductSearchDto();
        dto.setId(doc.getId());
        dto.setName(doc.getName());
        dto.setDescription(doc.getDescription());
        dto.setPrice(doc.getPrice());
        dto.setCategoryId(doc.getCategoryId());
        dto.setAttributes(doc.getAttributes() != null ? doc.getAttributes() : Map.of());
        return dto;
    }

    public static ProductDocument toDocument(ProductEvent event) {
        ProductDocument doc = new ProductDocument();
        doc.setId(event.getProductId());
        doc.setName(event.getName());
        doc.setDescription(event.getDescription());
        doc.setPrice(event.getPrice());
        doc.setCategoryId(event.getCategoryId());
        doc.setAttributes(event.getAttributes());
        doc.setActive(event.isActive());
        return doc;
    }
}
