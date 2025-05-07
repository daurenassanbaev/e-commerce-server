package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.exception.CartIsEmptyException;
import com.ecommerce.cartservice.exception.CheckoutFailedException;
import com.ecommerce.cartservice.exception.InvalidCartItemException;
import com.ecommerce.cartservice.model.dto.CartItemDto;
import com.ecommerce.cartservice.model.dto.request.CartRequestDto;
import com.ecommerce.cartservice.model.dto.response.CartResponseDto;
import com.ecommerce.cartservice.service.client.OrderFeignClient;
import com.ecommerce.cartservice.service.client.UserFeignClient;
import com.ecommerce.cartservice.util.RedisCartUtil;
import com.ecommerce.common.model.dto.request.OrderItemRequestDto;
import com.ecommerce.common.model.dto.request.OrderRequestDto;
import com.ecommerce.common.model.dto.response.OrderResponseDto;
import com.ecommerce.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserFeignClient userFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final RedisCartUtil redisCartUtil;

    public CartResponseDto getCart(String token) {
        Long userId = fetchUserId(token);

        List<CartItemDto> items = redisCartUtil.getCartItems(userId);

        return new CartResponseDto(items);
    }

    public void addToCart(String token, CartRequestDto request) {
        checkQuantity(request);

        Long userId = fetchUserId(token);
        Long productId = request.getProductId();

        CartItemDto existingItem = redisCartUtil.getCartItem(userId, productId);

        Integer newQuantity = request.getQuantity();
        if (existingItem != null) {
            newQuantity += existingItem.getQuantity();
        }

        redisCartUtil.putCartItem(userId, new CartItemDto(productId, newQuantity));
    }

    public void updateQuantity(String token, CartRequestDto request) {
        checkQuantity(request);
        Long userId = fetchUserId(token);
        redisCartUtil.updateQuantity(userId, request.getProductId(), request.getQuantity());
    }

    public void removeItem(String token, Long productId) {
        Long userId = fetchUserId(token);
        redisCartUtil.removeCartItem(userId, productId);
    }

    public OrderResponseDto checkout(String token) {
        Long userId = fetchUserId(token);
        List<CartItemDto> items = redisCartUtil.getCartItems(userId);

        if (items == null || items.isEmpty()) {
            throw new CartIsEmptyException("Cart is empty");
        }

        List<OrderItemRequestDto> orderItemRequestDtos = items.stream()
                .map(item -> {
                    OrderItemRequestDto orderItemRequestDto = new OrderItemRequestDto();
                    orderItemRequestDto.setProductId(item.getProductId());
                    orderItemRequestDto.setQuantity(item.getQuantity());
                    return orderItemRequestDto;
                })
                .toList();

        OrderRequestDto orderRequestDto = new OrderRequestDto(userId, orderItemRequestDtos);

        try {
            OrderResponseDto orderResponseDto = orderFeignClient.create(orderRequestDto);
            redisCartUtil.clearCart(userId);
            return orderResponseDto;
        } catch (Exception ex) {
            throw new CheckoutFailedException("Failed to checkout. Reason: " + ex.getMessage(), ex);
        }
    }

    private Long fetchUserId(String token) {
        UUID userId = JwtUtil.extractSubject(token);
        return userFeignClient.getUserByKeycloakId(userId);
    }

    private void checkQuantity(CartRequestDto request) {
        if (request.getQuantity() == null || request.getQuantity() < 1) {
            throw new InvalidCartItemException("Quantity must be at least 1");
        }
    }

}
