package org.samtar.warehouse.orders.repository;
import org.samtar.warehouse.orders.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity,Long> {

}
