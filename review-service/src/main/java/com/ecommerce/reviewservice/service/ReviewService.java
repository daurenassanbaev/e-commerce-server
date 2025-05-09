package com.ecommerce.reviewservice.service;

import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.model.dto.response.PagedResponse;
import com.ecommerce.common.model.dto.response.ProductStatusResponseDto;
import com.ecommerce.common.util.JwtUtil;
import com.ecommerce.common.util.PaginationUtil;
import com.ecommerce.reviewservice.exception.DuplicateReviewException;
import com.ecommerce.reviewservice.model.converter.ReviewConverter;
import com.ecommerce.reviewservice.model.document.Review;
import com.ecommerce.reviewservice.model.dto.request.ReviewRequestDto;
import com.ecommerce.reviewservice.model.dto.response.ReviewResponseDto;
import com.ecommerce.reviewservice.repository.ReviewRepository;
import com.ecommerce.reviewservice.service.client.ProductFeignClient;
import com.ecommerce.reviewservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserFeignClient userFeignClient;
    private final ProductFeignClient productFeignClient;

    public PagedResponse<ReviewResponseDto> getAllByProductId(Long productId, Pageable pageable) {
        Page<Review> page = reviewRepository.findAllByProductId(productId, pageable);
        List<ReviewResponseDto> content = page.map(ReviewConverter::toDto).getContent();
        return PaginationUtil.buildPagedResponse(page, content);
    }

    @Transactional
    public ReviewResponseDto addReview(Long productId, ReviewRequestDto dto, String token) {
        Long userId = getUserIdFromToken(token);

        validateProductIsActive(productId);
        validateNotReviewed(productId, userId);

        Review review = Review.builder()
                .productId(productId)
                .userId(userId)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        return ReviewConverter.toDto(reviewRepository.save(review));
    }

    @Transactional
    public void deleteReview(String reviewId) {
        Review review = getReviewOrThrow(reviewId);
        reviewRepository.delete(review);
    }

    private Long getUserIdFromToken(String token) {
        UUID keycloakId = JwtUtil.extractSubject(token);
        return userFeignClient.getUserByKeycloakId(keycloakId);
    }

    private void validateNotReviewed(Long productId, Long userId) {
        reviewRepository.findByProductIdAndUserId(productId, userId)
                .ifPresent(r -> {
                    throw new DuplicateReviewException(userId, productId);
                });
    }

    private Review getReviewOrThrow(String reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
    }

    private void validateProductIsActive(Long productId) {
        ProductStatusResponseDto dto = productFeignClient.getProductStatus(productId);
        if (!dto.getIsActive()) {
            throw new ResourceNotFoundException("Product", "Id", productId.toString());
        }
    }
}
