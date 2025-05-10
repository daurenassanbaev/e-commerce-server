package com.ecommerce.searchservice.review.service;

import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.model.dto.response.PagedResponse;
import com.ecommerce.common.model.event.review.ReviewDeletedEvent;
import com.ecommerce.common.model.event.review.ReviewEvent;
import com.ecommerce.common.util.PaginationUtil;
import com.ecommerce.searchservice.review.model.converter.ReviewSearchConverter;
import com.ecommerce.searchservice.review.model.document.ReviewDocument;
import com.ecommerce.searchservice.review.model.dto.ReviewSearchDto;
import com.ecommerce.searchservice.review.repository.ReviewSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewSearchService {
    private final ReviewSearchRepository searchRepository;

    public PagedResponse<ReviewSearchDto> searchByKeyword(String keyword, Pageable pageable) {
        Page<ReviewDocument> page = searchRepository.searchByKeyword(keyword, pageable);

        List<ReviewSearchDto> content = page.getContent()
                .stream()
                .map(ReviewSearchConverter::toDto)
                .toList();

        return PaginationUtil.buildPagedResponse(page, content);
    }

    public void save(ReviewEvent event) {
        ReviewDocument doc = ReviewSearchConverter.toDocument(event);
        searchRepository.save(doc);
    }

    public void delete(ReviewDeletedEvent event) {
        String id = event.getReviewId();

        ReviewDocument doc = searchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewDocument", "ID", id));

        searchRepository.delete(doc);
    }
}
