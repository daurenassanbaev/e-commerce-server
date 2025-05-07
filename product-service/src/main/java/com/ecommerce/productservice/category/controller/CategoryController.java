package com.ecommerce.productservice.category.controller;

import com.ecommerce.common.model.dto.response.PagedResponse;
import com.ecommerce.productservice.category.model.dto.CategoryDto;
import com.ecommerce.productservice.category.model.dto.request.CategoryRequestDto;
import com.ecommerce.productservice.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<CategoryDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") Long id, @RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<CategoryDto> archive(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.archive(id));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<CategoryDto> activate(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.activate(id));
    }
}
