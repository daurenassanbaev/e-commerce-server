package com.ecommerce.searchservice.review.model.converter;

import com.ecommerce.common.model.event.review.ReviewEvent;
import com.ecommerce.searchservice.review.model.document.ReviewDocument;
import com.ecommerce.searchservice.review.model.dto.ReviewSearchDto;

public class ReviewSearchConverter {

    public static ReviewSearchDto toDto(ReviewDocument doc) {
        ReviewSearchDto dto = new ReviewSearchDto();
        dto.setReviewId(doc.getReviewId());
        dto.setProductId(doc.getProductId());
        dto.setUserId(doc.getUserId());
        dto.setRating(doc.getRating());
        dto.setComment(doc.getComment());
        return dto;
    }

    public static ReviewDocument toDocument(ReviewEvent event) {
        ReviewDocument doc = new ReviewDocument();
        doc.setReviewId(event.getReviewId());
        doc.setProductId(event.getProductId());
        doc.setUserId(event.getUserId());
        doc.setRating(event.getRating());
        doc.setComment(event.getComment());
        return doc;
    }
}
