package org.samtar.warehouse.cart.repository;

import java.util.Optional;

import org.samtar.warehouse.cart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUser_id(Long userId);
    void deleteByUser_id(Long userId);
}
