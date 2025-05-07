package com.ecommerce.inventoryservice.service;

import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.model.dto.response.InventoryResponseDto;
import com.ecommerce.common.model.dto.response.ReleaseResponseDto;
import com.ecommerce.common.model.dto.response.ReserveResponseDto;
import com.ecommerce.inventoryservice.model.converter.InventoryConverter;
import com.ecommerce.inventoryservice.model.entity.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryResponseDto getInventory(Long productId) {
        Inventory inventory = getInventoryOrThrow(productId);
        return InventoryConverter.toDto(inventory);
    }

    @Transactional
    public ReserveResponseDto reserve(Long productId, Integer quantity) {
        Inventory inventory = getInventoryOrThrow(productId);

        if (inventory.getAvailableQty() < quantity) {
            return new ReserveResponseDto(false, inventory.getReservedQty());
        }

        updateInventoryForReservation(inventory, quantity);

        return new ReserveResponseDto(true, inventory.getReservedQty());

    }

    @Transactional
    public ReleaseResponseDto release(Long productId, Integer quantity) {
        Inventory inventory = getInventoryOrThrow(productId);

        updateInventoryForRelease(inventory, quantity);
        return new ReleaseResponseDto(true, inventory.getAvailableQty());
    }

    @Transactional
    public void upsertInventory(Long productId, Integer availableQty) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseGet(() -> createNewInventory(productId));

        inventory.setAvailableQty(availableQty);
        inventory.setUpdatedAt(LocalDateTime.now());

        inventoryRepository.save(inventory);
    }

    private Inventory getInventoryOrThrow(Long productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "productId", productId.toString()));
    }


    private void updateInventoryForReservation(Inventory inventory, int quantity) {
        inventory.setAvailableQty(inventory.getAvailableQty() - quantity);
        inventory.setReservedQty(inventory.getReservedQty() + quantity);
        inventory.setUpdatedAt(LocalDateTime.now());
        inventoryRepository.save(inventory);
    }

    private void updateInventoryForRelease(Inventory inventory, int quantity) {
        inventory.setReservedQty(inventory.getReservedQty() - quantity);
        inventory.setAvailableQty(inventory.getAvailableQty() + quantity);
        inventory.setUpdatedAt(LocalDateTime.now());
        inventoryRepository.save(inventory);
    }

    private Inventory createNewInventory(Long productId) {
        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setReservedQty(0);
        return inventory;
    }

}
