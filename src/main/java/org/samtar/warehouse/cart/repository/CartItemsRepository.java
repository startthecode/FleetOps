package org.samtar.warehouse.cart.repository;

import org.samtar.warehouse.cart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository  extends JpaRepository<CartEntity,Long>{
}
