package com.ecommerce.orderservice.order.service;

import com.ecommerce.common.model.dto.response.OrderResponseDto;
import com.ecommerce.common.model.dto.response.PagedResponse;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.util.JwtUtil;
import com.ecommerce.common.util.PaginationUtil;
import com.ecommerce.orderservice.order.model.converter.OrderConverter;
import com.ecommerce.orderservice.order.model.dto.OrderDto;
import com.ecommerce.orderservice.order.model.dto.request.OrderStatusUpdateRequestDto;
import com.ecommerce.orderservice.order.model.entity.Order;
import com.ecommerce.orderservice.order.repository.OrderRepository;
import com.ecommerce.orderservice.order.service.client.ProductFeignClient;
import com.ecommerce.orderservice.order.service.client.UserFeignClient;
import com.ecommerce.orderservice.orderitem.model.dto.OrderItemDto;
import com.ecommerce.orderservice.orderitem.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserFeignClient userFeignClient;

    public PagedResponse<OrderDto> getAll(String token, Pageable pageable) {
        Long userId = extractUserId(token);

        Page<Order> page = orderRepository.findAllByUserId(pageable, userId);
        List<OrderDto> content = page.getContent()
                .stream()
                .map(this::buildOrderDto)
                .toList();

        return PaginationUtil.buildPagedResponse(page, content);
    }

    public OrderDto getById(Long id) {
        Order order = findById(id);
        return buildOrderDto(order);
    }

    @Transactional
    public OrderResponseDto updateStatus(Long id, OrderStatusUpdateRequestDto dto) {
        Order order = findById(id);

        order.setStatus(dto.getStatus());
        orderRepository.save(order);

        return new OrderResponseDto(
                id,
                dto.getStatus().name()
        );
    }

    private Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "ID", id.toString()));
    }

    private Long extractUserId(String token) {
        UUID keycloakId = JwtUtil.extractSubject(token);
        return userFeignClient.getUserByKeycloakId(keycloakId);
    }

    private OrderDto buildOrderDto(Order order) {
        List<OrderItemDto> orderItems = orderItemService.getAllByOrderId(order.getId());
        return OrderConverter.toDto(order, orderItems);
    }
}
