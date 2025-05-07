package com.ecommerce.cartservice.model.dto.request;

import lombok.Data;

@Data
public class CartRequestDto {
    private Long productId;
    private Integer quantity;
}