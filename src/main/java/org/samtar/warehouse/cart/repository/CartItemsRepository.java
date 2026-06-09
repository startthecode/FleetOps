package org.samtar.warehouse.cart.repository;

import org.samtar.warehouse.cart.entity.CartItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository  extends JpaRepository<CartItemsEntity,Long>{
    void deleteAllByCart_id(Long cartId);
}
