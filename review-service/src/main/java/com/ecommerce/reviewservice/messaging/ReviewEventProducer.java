package com.ecommerce.reviewservice.messaging;

import com.ecommerce.common.model.event.review.ReviewDeletedEvent;
import com.ecommerce.common.model.event.review.ReviewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewEventProducer {
    private final StreamBridge streamBridge;

    public void sendReviewCreatedEvent(ReviewEvent reviewEvent) {
        streamBridge.send("reviewCreated-out-0", reviewEvent);
    }

    public void sendReviewDeletedEvent(ReviewDeletedEvent reviewDeletedEvent) {
        streamBridge.send("reviewDeleted-out-0", reviewDeletedEvent);
    }
}
