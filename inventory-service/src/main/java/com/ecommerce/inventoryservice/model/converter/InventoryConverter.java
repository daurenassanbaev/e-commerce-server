package com.ecommerce.inventoryservice.model.converter;

import com.ecommerce.common.model.dto.response.InventoryResponseDto;
import com.ecommerce.inventoryservice.model.entity.Inventory;

public class InventoryConverter {

    public static InventoryResponseDto toDto(Inventory inventory) {
        InventoryResponseDto dto = new InventoryResponseDto();
        dto.setProductId(inventory.getProductId());
        dto.setAvailableQty(inventory.getAvailableQty());
        dto.setReservedQty(inventory.getReservedQty());
        return dto;
    }
}
