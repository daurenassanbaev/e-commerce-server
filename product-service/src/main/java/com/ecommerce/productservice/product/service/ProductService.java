package com.ecommerce.productservice.product.service;

import com.ecommerce.common.model.dto.ProductPriceDto;
import com.ecommerce.common.model.dto.response.PagedResponse;
import com.ecommerce.common.model.event.product.ProductEvent;
import com.ecommerce.common.model.event.product.ProductStatusChangedEvent;
import com.ecommerce.common.exception.AlreadyActivatedException;
import com.ecommerce.common.exception.AlreadyArchivedException;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.util.PaginationUtil;
import com.ecommerce.productservice.category.service.CategoryService;
import com.ecommerce.productservice.product.messaging.ProductEventProducer;
import com.ecommerce.productservice.product.model.converter.ProductConverter;
import com.ecommerce.productservice.product.model.converter.ProductEventConverter;
import com.ecommerce.productservice.product.model.converter.ProductPriceConverter;
import com.ecommerce.productservice.product.model.dto.ProductDto;
import com.ecommerce.productservice.product.model.dto.request.ProductRequestDto;
import com.ecommerce.productservice.product.model.entity.Product;
import com.ecommerce.productservice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductEventProducer productEventProducer;

    public ProductDto getById(Long id) {
        return ProductConverter.toDto(findById(id, true));
    }

    public PagedResponse<ProductDto> getAll(Pageable pageable) {
        Page<Product> page = productRepository.findAllAndIsActive(pageable);

        List<ProductDto> content = page.getContent()
                .stream()
                .map(ProductConverter::toDto)
                .toList();

        return PaginationUtil.buildPagedResponse(page, content);
    }

    @Transactional
    public ProductDto create(ProductRequestDto dto) {
        if (!categoryService.isExists(dto.getCategoryId())) {
            throw new ResourceNotFoundException("Category", "ID", dto.getCategoryId().toString());
        }
        Product p = new Product();
        fillProduct(p, dto);
        p.setActive(true);
        p.setCreatedAt(LocalDateTime.now());
        Product product = productRepository.save(p);

        ProductEvent productEvent = ProductEventConverter.toProductEvent(product);
        productEventProducer.sendProductCreatedEvent(productEvent);

        return ProductConverter.toDto(product);
    }

    @Transactional
    public ProductDto update(Long id, ProductRequestDto dto) {
        if (!categoryService.isExists(dto.getCategoryId())) {
            throw new ResourceNotFoundException("Category", "ID", dto.getCategoryId().toString());
        }
        Product p = findById(id, true);
        fillProduct(p, dto);
        p.setUpdatedAt(LocalDateTime.now());
        Product product = productRepository.save(p);

        ProductEvent event = ProductEventConverter.toProductEvent(product);
        productEventProducer.sendProductUpdatedEvent(event);

        return ProductConverter.toDto(product);
    }

    @Transactional
    public ProductDto archive(Long id) {
        Product p = findById(id, true);
        if (!p.isActive()) {
            throw new AlreadyArchivedException("Product with ID %d is already archived".formatted(id));
        }
        setActiveStatus(p, false);

        return ProductConverter.toDto(p);
    }

    @Transactional
    public ProductDto activate(Long id) {
        Product p = findById(id, false);
        if (p.isActive()) {
            throw new AlreadyActivatedException("Product with ID %d is already active".formatted(id));
        }
        setActiveStatus(p, true);
        return ProductConverter.toDto(p);
    }

    private Product findById(Long id, boolean isActive) {
        return productRepository.findByIdAndIsActive(id, isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", id.toString()));
    }

    private void fillProduct(Product p, ProductRequestDto dto) {
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setCategoryId(dto.getCategoryId());
        p.setAttributes(dto.getAttributes());
    }

    private void setActiveStatus(Product p, boolean isActive) {
        p.setActive(isActive);
        p.setUpdatedAt(LocalDateTime.now());
        Product product = productRepository.save(p);

        ProductStatusChangedEvent event = new ProductStatusChangedEvent(product.getId(), isActive);
        productEventProducer.sendProductStatusChangedEvent(event);
    }

    public List<ProductPriceDto> getPrices(List<Long> productIds) {
        return productRepository.findAllById(productIds)
                .stream()
                .map(ProductPriceConverter::toDto)
                .toList();
    }
}
