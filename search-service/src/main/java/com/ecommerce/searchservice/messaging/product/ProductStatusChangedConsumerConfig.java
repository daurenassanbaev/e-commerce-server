package com.ecommerce.searchservice.messaging.product;

import com.ecommerce.common.event.product.ProductStatusChangedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductStatusChangedConsumerConfig {

    @Bean
    public Consumer<ProductStatusChangedEvent> productStatusChangedConsumer() {
        return event -> {
            System.out.println("Received product event: " + event.getProductId());
            System.out.println("Received product event: " + event.isActive());
        };
    }
}
