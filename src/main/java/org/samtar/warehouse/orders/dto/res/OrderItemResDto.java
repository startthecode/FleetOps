package org.samtar.warehouse.orders.dto.res;

public record OrderItemResDto(
    Long productID,
    String productName,
    Integer quantity,
    Double price
) {

}
