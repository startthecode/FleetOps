package org.samtar.warehouse.products.repository;

import org.samtar.warehouse.products.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByProductNameIgnoreCase(String productName);
    Optional<ProductEntity> findByProductId(long productId);
    Boolean existsByProductNameIgnoreCase(String productName);
    Boolean existsByProductId(long productID);

    List<ProductEntity> findAllByCreatedBy_id(Long creatorID);
}
