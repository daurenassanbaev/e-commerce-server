package com.ecommerce.searchservice.product.messaging;

import com.ecommerce.common.event.product.ProductStatusChangedEvent;
import com.ecommerce.searchservice.product.service.ProductSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductStatusChangedConsumerConfig {

    @Bean
    public Consumer<ProductStatusChangedEvent> productStatusChangedConsumer(ProductSearchService productSearchService) {
        return productSearchService::changeStatus;
    }
}
