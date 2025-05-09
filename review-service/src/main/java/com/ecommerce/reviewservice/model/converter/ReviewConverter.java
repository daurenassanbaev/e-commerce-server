package com.ecommerce.reviewservice.model.converter;

import com.ecommerce.reviewservice.model.document.Review;
import com.ecommerce.reviewservice.model.dto.response.ReviewResponseDto;

public class ReviewConverter {

    public static ReviewResponseDto toDto(Review review) {
        ReviewResponseDto dto = new ReviewResponseDto();
        dto.setId(review.getId());
        dto.setProductId(review.getProductId());
        dto.setUserId(review.getUserId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}
