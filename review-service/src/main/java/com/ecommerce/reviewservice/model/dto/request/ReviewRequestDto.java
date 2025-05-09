package com.ecommerce.reviewservice.model.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewRequestDto {
    @Size(min = 1, max = 5)
    private Integer rating;
    private String comment;
}
