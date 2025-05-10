package com.ecommerce.common.model.event.review;

import lombok.Data;

@Data
public class ReviewEvent {
    private String reviewId;
    private Long productId;
    private Long userId;
    private Integer rating;
    private String comment;
}
