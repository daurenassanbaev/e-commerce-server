package com.ecommerce.searchservice.messaging.product;

import com.ecommerce.common.event.product.ProductEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductUpdatedConsumerConfig {

    @Bean
    public Consumer<ProductEvent> productUpdatedConsumer() {
        return event -> {
            System.out.println("Received product updated event: " + event.getProductId());
            System.out.println("Received product updated event: " + event.getName());
            System.out.println("Received product updated event: " + event.getPrice());
            System.out.println("Received product updated event: " + event.getDescription());
            System.out.println("Received product updated event: " + event.getAttributes());
            System.out.println("Received product updated event: isActive = " + event.isActive());
        };
    }
}
