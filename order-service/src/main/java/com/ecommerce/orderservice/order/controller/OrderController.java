package com.ecommerce.orderservice.order.controller;

import com.ecommerce.common.model.dto.request.OrderRequestDto;
import com.ecommerce.common.model.dto.response.OrderResponseDto;
import com.ecommerce.common.model.dto.response.PagedResponse;
import com.ecommerce.orderservice.order.model.dto.OrderDto;
import com.ecommerce.orderservice.order.model.dto.request.OrderStatusUpdateRequestDto;
import com.ecommerce.orderservice.order.saga.OrderSagaOrchestrator;
import com.ecommerce.orderservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderSagaOrchestrator orderSagaOrchestrator;

    @GetMapping
    public ResponseEntity<PagedResponse<OrderDto>> getAll(@RequestHeader("Authorization") String token,
                                                          Pageable pageable) {
        return ResponseEntity.ok(orderService.getAll(token, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@RequestBody OrderRequestDto orderDto) {
        return ResponseEntity.ok(orderSagaOrchestrator.create(orderDto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDto> updateStatus(@PathVariable Long id,
                                                        @RequestBody OrderStatusUpdateRequestDto dto) {
        return ResponseEntity.ok(orderService.updateStatus(id, dto));
    }
}
