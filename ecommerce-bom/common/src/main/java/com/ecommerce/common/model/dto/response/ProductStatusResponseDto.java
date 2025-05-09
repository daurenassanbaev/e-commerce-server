package com.ecommerce.common.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatusResponseDto {
    private Long productId;
    private Boolean isActive;
}
