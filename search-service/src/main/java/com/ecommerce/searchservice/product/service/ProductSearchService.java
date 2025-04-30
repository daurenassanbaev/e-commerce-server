package com.ecommerce.searchservice.product.service;

import com.ecommerce.common.dto.response.PagedResponse;
import com.ecommerce.common.event.product.ProductEvent;
import com.ecommerce.common.event.product.ProductStatusChangedEvent;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.searchservice.product.model.converter.ProductSearchConverter;
import com.ecommerce.searchservice.product.model.document.ProductDocument;
import com.ecommerce.searchservice.product.model.dto.ProductSearchDto;
import com.ecommerce.searchservice.product.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductSearchRepository searchRepository;

    public PagedResponse<ProductSearchDto> searchByKeyword(String keyword, Pageable pageable) {
        Page<ProductDocument> page = searchRepository.searchByKeyword(keyword, pageable);

        List<ProductSearchDto> content = page.getContent()
                .stream()
                .map(ProductSearchConverter::toDto)
                .toList();

        return new PagedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    public void save(ProductEvent event) {
        ProductDocument doc = ProductSearchConverter.toDocument(event);
        searchRepository.save(doc);
    }

    public void changeStatus(ProductStatusChangedEvent event) {
        Long id = event.getProductId();

        ProductDocument doc = searchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductDocument", "ID", id.toString()));

        doc.setActive(event.isActive());

        searchRepository.save(doc);
    }
}
