package com.ecommerce.searchservice.product.messaging;

import com.ecommerce.common.event.product.ProductEvent;
import com.ecommerce.searchservice.product.service.ProductSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductCreatedConsumerConfig {

    @Bean
    public Consumer<ProductEvent> productCreatedConsumer(ProductSearchService productSearchService) {
        return productSearchService::save;
    }
}
