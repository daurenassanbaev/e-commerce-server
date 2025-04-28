package com.ecommerce.common.event.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatusChangedEvent {
    private Long productId;
    private boolean isActive;
}
