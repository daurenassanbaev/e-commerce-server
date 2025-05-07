package com.ecommerce.orderservice.order.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW,
    CONFIRMED,
    CANCELLED
}
