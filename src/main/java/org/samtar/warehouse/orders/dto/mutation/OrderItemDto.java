package org.samtar.warehouse.orders.dto.mutation;

public record OrderItemDto(
        Long productId,
        Integer quantity,
        Double price) {

}
