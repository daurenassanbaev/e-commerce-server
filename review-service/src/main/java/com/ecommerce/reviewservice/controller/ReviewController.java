package com.ecommerce.reviewservice.controller;

import com.ecommerce.common.model.dto.response.PagedResponse;
import com.ecommerce.reviewservice.model.dto.request.ReviewRequestDto;
import com.ecommerce.reviewservice.model.dto.response.ReviewResponseDto;
import com.ecommerce.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    public ResponseEntity<PagedResponse<ReviewResponseDto>> getAllByProductId(@PathVariable("productId") Long productId,
                                                                              Pageable pageable) {
        return ResponseEntity.ok(reviewService.getAllByProductId(productId, pageable));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ReviewResponseDto> addReview(@RequestHeader("Authorization") String token,
                                                       @PathVariable("productId") Long productId,
                                                       @RequestBody ReviewRequestDto dto) {
        return ResponseEntity.ok(reviewService.addReview(productId, dto, token));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") String reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
