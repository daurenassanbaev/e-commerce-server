package com.ecommerce.inventoryservice.model.dto.request;

import lombok.Data;

@Data
public class InventoryUpsertRequestDto {
    private Long productId;
    private Integer availableQty;
}
