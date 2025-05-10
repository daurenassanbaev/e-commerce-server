package com.ecommerce.reviewservice.model.converter;

import com.ecommerce.common.model.event.review.ReviewEvent;
import com.ecommerce.reviewservice.model.document.Review;

public class ReviewEventConverter {

    public static ReviewEvent toReviewEvent(Review review) {
        ReviewEvent reviewEvent = new ReviewEvent();

        reviewEvent.setReviewId(review.getId());
        reviewEvent.setUserId(review.getUserId());
        reviewEvent.setProductId(review.getProductId());
        reviewEvent.setRating(review.getRating());
        reviewEvent.setComment(review.getComment());

        return reviewEvent;
    }
}
