package com.ecommerce.searchservice.product.controller;

import com.ecommerce.common.dto.response.PagedResponse;
import com.ecommerce.searchservice.product.model.dto.ProductSearchDto;
import com.ecommerce.searchservice.product.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/search")
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductSearchService productService;

    @GetMapping
    public ResponseEntity<PagedResponse<ProductSearchDto>> search(@RequestParam("q") String keyword,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(productService.searchByKeyword(keyword, pageable));
    }
}
