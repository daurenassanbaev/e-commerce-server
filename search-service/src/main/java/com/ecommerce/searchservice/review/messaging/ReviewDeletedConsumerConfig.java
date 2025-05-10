package com.ecommerce.searchservice.review.messaging;

import com.ecommerce.common.model.event.review.ReviewDeletedEvent;
import com.ecommerce.searchservice.review.service.ReviewSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ReviewDeletedConsumerConfig {

    @Bean
    public Consumer<ReviewDeletedEvent> reviewDeletedConsumer(ReviewSearchService reviewSearchService) {
        return reviewSearchService::delete;
    }
}