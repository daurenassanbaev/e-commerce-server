package com.ecommerce.searchservice.review.controller;

import com.ecommerce.common.model.dto.response.PagedResponse;
import com.ecommerce.searchservice.review.model.dto.ReviewSearchDto;
import com.ecommerce.searchservice.review.service.ReviewSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/reviews")
@RequiredArgsConstructor
public class ReviewSearchController {

    private final ReviewSearchService reviewService;

    @GetMapping
    public ResponseEntity<PagedResponse<ReviewSearchDto>> search(@RequestParam("q") String keyword,
                                                                 Pageable pageable) {
        return ResponseEntity.ok(reviewService.searchByKeyword(keyword, pageable));
    }
}