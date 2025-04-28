package com.ecommerce.productservice.product.model.converter;

import com.ecommerce.common.event.product.ProductEvent;
import com.ecommerce.productservice.product.model.entity.Product;

public class ProductEventConverter {

    public static ProductEvent toProductEvent(Product product) {
        ProductEvent event = new ProductEvent();
        event.setProductId(product.getId());
        event.setName(product.getName());
        event.setDescription(product.getDescription());
        event.setPrice(product.getPrice());
        event.setCategoryId(product.getCategoryId());
        event.setActive(product.isActive());
        event.setAttributes(product.getAttributes());
        return event;
    }
}
