package com.ecommerce.orderservice.order.model.dto.request;

import com.ecommerce.orderservice.order.model.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusUpdateRequestDto {
    private OrderStatus status;
}
