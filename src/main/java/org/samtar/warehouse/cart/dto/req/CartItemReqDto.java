package org.samtar.warehouse.cart.dto.req;

public record CartItemReqDto(
        Long productId,
        Integer quantity,
        Double price) {

}
