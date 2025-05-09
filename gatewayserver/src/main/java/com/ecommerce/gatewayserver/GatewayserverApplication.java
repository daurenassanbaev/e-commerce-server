package com.ecommerce.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("user-service", r -> r.path("/api/users/**", "/api/auth/**")
                        .uri("lb://USER-SERVICE")
                )
                .route("product-service", r -> r.path("/api/products/**", "/api/categories/**")
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route("search-service", r -> r.path("/api/search/**")
                        .uri("lb://SEARCH-SERVICE")
                )
                .route("inventory-service", r -> r.path("/api/inventory/**")
                        .uri("lb://INVENTORY-SERVICE")
                )
                .route("order-service", r -> r.path("/api/orders/**")
                        .uri("lb://ORDER-SERVICE")
                )
                .route("cart-service", r -> r.path("/api/cart/**")
                        .uri("lb://CART-SERVICE")
                )
                .route("review-service", r -> r.path("/api/reviews/**")
                        .uri("lb://REVIEW-SERVICE")
                )
                .build();
    }
}
