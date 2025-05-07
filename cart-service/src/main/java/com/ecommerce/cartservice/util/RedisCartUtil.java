package com.ecommerce.cartservice.util;

import com.ecommerce.cartservice.model.dto.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisCartUtil {

    private final RedisTemplate<String, CartItemDto> redisTemplate;
    private static final String CART_KEY_PREFIX = "cart:";

    public List<CartItemDto> getCartItems(Long userId) {
        HashOperations<String, String, CartItemDto> hashOps = redisTemplate.opsForHash();
        return hashOps
                .values(buildKey(userId))
                .stream()
                .toList();
    }

    public CartItemDto getCartItem(Long userId, Long productId) {
        HashOperations<String, String, CartItemDto> hashOps = redisTemplate.opsForHash();
        return hashOps
                .get(buildKey(userId), productId.toString());
    }

    public String buildKey(Long userId) {
        return CART_KEY_PREFIX + userId;
    }

    public void putCartItem(Long userId, CartItemDto cartItemDto) {
        HashOperations<String, String, CartItemDto> hashOps = redisTemplate.opsForHash();
        hashOps.put(buildKey(userId), cartItemDto.getProductId().toString(), cartItemDto);
    }

    public void updateQuantity(Long userId, Long productId, Integer quantity) {
        CartItemDto cartItemDto = getCartItem(userId, productId);
        cartItemDto.setQuantity(quantity);
        putCartItem(userId, cartItemDto);
    }

    public void removeCartItem(Long userId, Long productId) {
        HashOperations<String, String, CartItemDto> hashOps = redisTemplate.opsForHash();
        hashOps.delete(buildKey(userId), productId.toString());
    }

    public void clearCart(Long userId) {
        redisTemplate.delete(buildKey(userId));
    }
}
