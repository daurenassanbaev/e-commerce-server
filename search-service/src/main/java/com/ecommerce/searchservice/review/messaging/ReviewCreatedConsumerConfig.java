package com.ecommerce.searchservice.review.messaging;

import com.ecommerce.common.model.event.review.ReviewEvent;
import com.ecommerce.searchservice.review.service.ReviewSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ReviewCreatedConsumerConfig {

    @Bean
    public Consumer<ReviewEvent> reviewCreatedConsumer(ReviewSearchService reviewSearchService) {
        return reviewSearchService::save;
    }
}
