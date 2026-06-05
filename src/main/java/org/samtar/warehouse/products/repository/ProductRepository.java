package org.samtar.warehouse.products.repository;

import org.samtar.warehouse.products.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByProductName(String productName);
    Boolean existsByProductName(String productName);
}
