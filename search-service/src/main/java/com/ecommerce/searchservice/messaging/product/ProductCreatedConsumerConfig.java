package com.ecommerce.searchservice.messaging.product;

import com.ecommerce.common.event.product.ProductEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductCreatedConsumerConfig {

    @Bean
    public Consumer<ProductEvent> productCreatedConsumer() {
        return event -> {
            System.out.println("Received product created event: " + event.getProductId());
            System.out.println("Received product created event: " + event.getName());
            System.out.println("Received product created event: " + event.getPrice());
            System.out.println("Received product created event: " + event.getDescription());
            System.out.println("Received product created event: " + event.getAttributes());
        };
    }
}
