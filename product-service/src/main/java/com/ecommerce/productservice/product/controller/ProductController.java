package com.ecommerce.productservice.product.controller;


import com.ecommerce.common.dto.response.PagedResponse;
import com.ecommerce.productservice.product.model.dto.ProductDto;
import com.ecommerce.productservice.product.model.dto.request.ProductRequestDto;
import com.ecommerce.productservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ProductDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable("id") Long id, @RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<ProductDto> archive(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.archive(id));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProductDto> activate(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.activate(id));
    }
}
