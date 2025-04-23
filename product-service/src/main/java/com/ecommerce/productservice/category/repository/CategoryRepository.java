package com.ecommerce.productservice.category.repository;

import com.ecommerce.productservice.category.model.entity.Category;
import com.ecommerce.productservice.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories c WHERE c.id = :id AND is_active = :isActive", nativeQuery = true)
    Optional<Category> findByIdAndIsActive(@Param("id") Long id, @Param("isActive") boolean isActive);

    @Query(value = "SELECT * FROM categories c WHERE c.is_active = true", nativeQuery = true)
    Page<Category> findAllAndIsActive(Pageable pageable);
}
