package com.ecommerce.orderservice.orderitem.service;

import com.ecommerce.common.model.dto.request.OrderItemRequestDto;
import com.ecommerce.orderservice.orderitem.model.converter.OrderItemConverter;
import com.ecommerce.orderservice.orderitem.model.dto.OrderItemDto;
import com.ecommerce.orderservice.orderitem.model.entity.OrderItem;
import com.ecommerce.orderservice.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItemDto> getAllByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId)
                .stream()
                .map(OrderItemConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveAll(Long orderId, List<OrderItemRequestDto> items, Map<Long, BigDecimal> priceMap) {
        List<OrderItem> orderItems = constructOrderItems(items, orderId, priceMap);

        orderItemRepository.saveAll(orderItems);
    }

    private List<OrderItem> constructOrderItems(List<OrderItemRequestDto> items, Long orderId, Map<Long, BigDecimal> priceMap) {
        return items.stream()
                .map(item -> {
                    Long productId = item.getProductId();
                    Integer quantity = item.getQuantity();

                    OrderItem orderItem = new OrderItem();

                    orderItem.setOrderId(orderId);
                    orderItem.setPrice(priceMap.get(productId));
                    orderItem.setQuantity(quantity);
                    orderItem.setProductId(productId);

                    return orderItem;
                })
                .toList();
    }
}
