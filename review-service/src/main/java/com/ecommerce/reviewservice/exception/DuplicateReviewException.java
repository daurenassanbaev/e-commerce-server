package com.ecommerce.reviewservice.exception;

public class DuplicateReviewException extends RuntimeException {
    public DuplicateReviewException(Long userId, Long productId) {
        super("User with ID %d has already reviewed product with ID %d".formatted(userId, productId));
    }
}
