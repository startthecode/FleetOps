package org.samtar.warehouse.cart.dto.res;

public record CartItemResDto(
    Long productID,
    String productName,
    Integer quantity,
    Double price
) {

}
