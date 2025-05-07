package com.ecommerce.orderservice.orderitem.model.converter;

import com.ecommerce.orderservice.orderitem.model.dto.OrderItemDto;
import com.ecommerce.orderservice.orderitem.model.entity.OrderItem;

public class OrderItemConverter {

    public static OrderItemDto toDto(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(orderItem.getId());
        dto.setPrice(orderItem.getPrice());
        dto.setOrderId(orderItem.getOrderId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setProductId(orderItem.getProductId());
        return dto;
    }
}
