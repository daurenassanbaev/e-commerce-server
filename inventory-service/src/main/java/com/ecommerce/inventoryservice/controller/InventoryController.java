package com.ecommerce.inventoryservice.controller;

import com.ecommerce.common.model.dto.request.ReleaseRequestDto;
import com.ecommerce.common.model.dto.request.ReserveRequestDto;
import com.ecommerce.common.model.dto.response.InventoryResponseDto;
import com.ecommerce.common.model.dto.response.ReleaseResponseDto;
import com.ecommerce.common.model.dto.response.ReserveResponseDto;
import com.ecommerce.inventoryservice.model.dto.request.InventoryUpsertRequestDto;
import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDto> getInventory(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventory(productId));
    }

    @PostMapping
    public ResponseEntity<Void> upsertInventory(@RequestBody InventoryUpsertRequestDto request) {
        inventoryService.upsertInventory(request.getProductId(), request.getAvailableQty());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/internal/{productId}/reserve")
    public ResponseEntity<ReserveResponseDto> reserve(
            @PathVariable Long productId,
            @RequestBody ReserveRequestDto request
    ) {
        return ResponseEntity.ok(inventoryService.reserve(productId, request.getQuantity()));
    }

    @PostMapping("/internal/{productId}/release")
    public ResponseEntity<ReleaseResponseDto> release(
            @PathVariable Long productId,
            @RequestBody ReleaseRequestDto request
    ) {
        return ResponseEntity.ok(inventoryService.release(productId, request.getQuantity()));
    }
}
