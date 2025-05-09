package com.ecommerce.orderservice.order.saga;

import com.ecommerce.common.exception.AlreadyArchivedException;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.model.dto.ProductPriceDto;
import com.ecommerce.common.model.dto.request.*;
import com.ecommerce.common.model.dto.response.OrderResponseDto;
import com.ecommerce.common.model.dto.response.ReserveResponseDto;
import com.ecommerce.orderservice.order.exception.EmptyOrderItemException;
import com.ecommerce.orderservice.order.exception.InventoryReserveException;
import com.ecommerce.orderservice.order.exception.OrderCreationException;
import com.ecommerce.orderservice.order.model.entity.Order;
import com.ecommerce.orderservice.order.model.enums.OrderStatus;
import com.ecommerce.orderservice.order.repository.OrderRepository;
import com.ecommerce.orderservice.order.service.client.InventoryFeignClient;
import com.ecommerce.orderservice.order.service.client.ProductFeignClient;
import com.ecommerce.orderservice.order.service.client.UserFeignClient;
import com.ecommerce.orderservice.orderitem.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderSagaOrchestrator {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserFeignClient userFeignClient;
    private final ProductFeignClient productFeignClient;
    private final InventoryFeignClient inventoryFeignClient;

    @Transactional
    public OrderResponseDto create(OrderRequestDto orderDto) {
        Long userId = extractUserId(orderDto);
        List<OrderItemRequestDto> items = orderDto.getItems();

        if (items.isEmpty()) {
            throw new EmptyOrderItemException("Order must contain at least one item.");
        }

        // Extract product ids
        List<Long> productIds = extractProductIds(orderDto);

        // Get product prices & price map
        ProductIdsRequestDto productIdsRequestDto = new ProductIdsRequestDto(productIds);
        List<ProductPriceDto> prices = productFeignClient.getPrices(productIdsRequestDto);
        Map<Long, BigDecimal> priceMap = collectPriceMap(prices);

        // Reserve products
        reserveProducts(items);

        // Calculate total amount
        BigDecimal totalAmount = calculateTotalAmount(items, priceMap);

        // Save order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);

        try {
            order = orderRepository.save(order);
            // Save order items
            orderItemService.saveAll(order.getId(), items, priceMap);
        } catch (Exception e) {
            releaseProducts(items);
            throw new OrderCreationException("Failed to create order, inventory released");
        }

        // Return
        return new OrderResponseDto(order.getId(), order.getStatus().name());
    }

    private Long extractUserId(OrderRequestDto dto) {
        Long userId = dto.getUserId();
        // Check user status
        checkUserExistence(userId);
        return userId;
    }

    private void checkUserExistence(Long userId) {
        if (!userFeignClient.isUserActive(userId)) {
            throw new ResourceNotFoundException("User", "ID", userId.toString());
        }
    }

    private List<Long> extractProductIds(OrderRequestDto orderDto) {
        return orderDto.getItems().stream()
                .map(OrderItemRequestDto::getProductId)
                .toList();
    }

    private void reserveProducts(List<OrderItemRequestDto> items) {
        try {
            for (OrderItemRequestDto item : items) {
                ReserveResponseDto dto = inventoryFeignClient.reserve(item.getProductId(), new ReserveRequestDto(item.getQuantity()));
                if (!dto.getSuccess()) {
                    throw new InventoryReserveException("Failed to reserve inventory: no products available in inventory.");
                }
            }
        } catch (Exception e) {
            throw new InventoryReserveException("Failed to reserve inventory.");
        }
    }

    private void releaseProducts(List<OrderItemRequestDto> items) {
        for (OrderItemRequestDto item : items) {
            inventoryFeignClient.release(item.getProductId(), new ReleaseRequestDto(item.getQuantity()));
        }
    }

    private Map<Long, BigDecimal> collectPriceMap(List<ProductPriceDto> prices) {
        return prices.stream()
                .collect(Collectors.toMap(ProductPriceDto::getProductId, ProductPriceDto::getPrice));
    }

    private BigDecimal calculateTotalAmount(List<OrderItemRequestDto> items, Map<Long, BigDecimal> priceMap) {
        return items
                .stream()
                .map(item -> {
                    BigDecimal price = priceMap.get(item.getProductId());
                    if (price == null) {
                        throw new ResourceNotFoundException("Product", "ID", item.getProductId().toString());
                    }
                    return price.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
