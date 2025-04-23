package com.ecommerce.productservice.category.service;

import com.ecommerce.common.dto.response.PagedResponse;
import com.ecommerce.common.exception.AlreadyActivatedException;
import com.ecommerce.common.exception.AlreadyArchivedException;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.productservice.category.model.converter.CategoryConverter;
import com.ecommerce.productservice.category.model.dto.CategoryDto;
import com.ecommerce.productservice.category.model.dto.request.CategoryRequestDto;
import com.ecommerce.productservice.category.model.entity.Category;
import com.ecommerce.productservice.category.repository.CategoryRepository;
import com.ecommerce.productservice.product.model.converter.ProductConverter;
import com.ecommerce.productservice.product.model.dto.ProductDto;
import com.ecommerce.productservice.product.model.entity.Product;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto getById(Long id) {
        return CategoryConverter.entityToDto(findById(id, true));
    }

    public PagedResponse<CategoryDto> getAll(Pageable pageable) {
        Page<Category> page = categoryRepository.findAllAndIsActive(pageable);
        List<CategoryDto> content = page.getContent()
                .stream()
                .map(CategoryConverter::entityToDto)
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

    @Transactional
    public CategoryDto create(CategoryRequestDto dto) {
        Category category = new Category();
        fillCategory(category, dto);
        category.setActive(true);
        category.setCreatedAt(LocalDateTime.now());
        return CategoryConverter.entityToDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto update(Long id, CategoryRequestDto dto) {
        Category category = findById(id, true);
        fillCategory(category, dto);
        category.setUpdatedAt(LocalDateTime.now());
        return CategoryConverter.entityToDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto archive(Long id) {
        Category category = findById(id, true);
        if (!category.isActive()) {
            throw new AlreadyArchivedException("Category with ID %d is already archived".formatted(id));
        }
        setActiveStatus(category, false);
        return CategoryConverter.entityToDto(category);
    }

    @Transactional
    public CategoryDto activate(Long id) {
        Category category = findById(id, false);
        if (category.isActive()) {
            throw new AlreadyActivatedException("Category with ID %d is already active".formatted(id));
        }
        setActiveStatus(category, true);
        return CategoryConverter.entityToDto(category);
    }

    public boolean isExists(Long id) {
        return findById(id, true) != null;
    }

    private Category findById(Long id, boolean isActive) {
        return categoryRepository.findByIdAndIsActive(id, isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id.toString()));
    }

    private void fillCategory(Category category, CategoryRequestDto dto) {
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
    }

    private void setActiveStatus(Category category, boolean isActive) {
        category.setActive(isActive);
        category.setUpdatedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }
}
