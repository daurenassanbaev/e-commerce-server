package com.ecommerce.cartservice.controller;

import com.ecommerce.cartservice.model.dto.request.CartRequestDto;
import com.ecommerce.cartservice.model.dto.response.CartResponseDto;
import com.ecommerce.cartservice.service.CartService;
import com.ecommerce.common.model.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(cartService.getCart(token));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(
            @RequestHeader("Authorization") String token,
            @RequestBody CartRequestDto request) {
        cartService.addToCart(token, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateQuantity(
            @RequestHeader("Authorization") String token,
            @RequestBody CartRequestDto request) {
        cartService.updateQuantity(token, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item/{productId}")
    public ResponseEntity<Void> deleteItem(
            @RequestHeader("Authorization") String token,
            @PathVariable Long productId) {
        cartService.removeItem(token, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDto> checkout(
            @RequestHeader("Authorization") String token) {
        OrderResponseDto response = cartService.checkout(token);
        return ResponseEntity.ok(response);
    }
}
