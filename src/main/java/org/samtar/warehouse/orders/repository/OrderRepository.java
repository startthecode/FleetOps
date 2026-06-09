package org.samtar.warehouse.orders.repository;

import java.util.Optional;
import java.util.Set;

import org.samtar.warehouse.common.enums.OrderStatus;
import org.samtar.warehouse.orders.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    Optional<OrderEntity> findByIdAndStatusNot(Long id,OrderStatus status);
    
    Set<OrderEntity> findByOwner_id(Long id);
    
}
