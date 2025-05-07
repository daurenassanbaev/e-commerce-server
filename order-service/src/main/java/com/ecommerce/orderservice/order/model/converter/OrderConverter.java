package com.ecommerce.orderservice.order.model.converter;

import com.ecommerce.orderservice.order.model.dto.OrderDto;
import com.ecommerce.orderservice.order.model.entity.Order;
import com.ecommerce.orderservice.orderitem.model.dto.OrderItemDto;

import java.util.List;

public class OrderConverter {

    public static OrderDto toDto(Order order, List<OrderItemDto> list) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setOrderItems(list);
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setUpdatedAt(order.getUpdatedAt());
        return orderDto;
    }
}
