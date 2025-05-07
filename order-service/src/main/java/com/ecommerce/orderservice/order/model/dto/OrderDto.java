package com.ecommerce.orderservice.order.model.dto;


import com.ecommerce.orderservice.order.model.enums.OrderStatus;
import com.ecommerce.orderservice.orderitem.model.dto.OrderItemDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<OrderItemDto> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}