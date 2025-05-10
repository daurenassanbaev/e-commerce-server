package com.ecommerce.searchservice.review.model.dto;

import lombok.Data;

@Data
public class ReviewSearchDto {
    private String reviewId;
    private Long productId;
    private Long userId;
    private Integer rating;
    private String comment;
}