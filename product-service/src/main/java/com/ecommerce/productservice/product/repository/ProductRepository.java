package com.ecommerce.productservice.product.repository;

import com.ecommerce.productservice.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products p WHERE p.id = :id AND is_active = :isActive", nativeQuery = true)
    Optional<Product> findByIdAndIsActive(@Param("id") Long id, @Param("isActive") boolean isActive);

    @Query(value = "SELECT * FROM products p WHERE p.is_active = true", nativeQuery = true)
    Page<Product> findAllAndIsActive(Pageable pageable);

    @Query(value = "SELECT * FROM products p WHERE p.is_active = true AND p.id IN :productIds", nativeQuery = true)
    List<Product> findAllByIdAndIsActive(@Param("productIds") List<Long> productIds, @Param("b") boolean b);
}
