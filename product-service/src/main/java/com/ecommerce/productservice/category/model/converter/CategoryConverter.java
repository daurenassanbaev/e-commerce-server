package com.ecommerce.productservice.category.model.converter;

import com.ecommerce.productservice.category.model.dto.CategoryDto;
import com.ecommerce.productservice.category.model.entity.Category;

public class CategoryConverter {

    public static CategoryDto entityToDto(Category category) {
        CategoryDto dto = new CategoryDto();

        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());

        return dto;
    }
}