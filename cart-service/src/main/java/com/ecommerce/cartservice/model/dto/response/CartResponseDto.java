package com.ecommerce.cartservice.model.dto.response;

import com.ecommerce.cartservice.model.dto.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartResponseDto {
    private List<CartItemDto> items;
}
