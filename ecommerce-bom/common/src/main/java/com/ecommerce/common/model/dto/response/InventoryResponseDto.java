package com.ecommerce.common.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDto {
    private Long productId;
    private int availableQty;
    private int reservedQty;
}
