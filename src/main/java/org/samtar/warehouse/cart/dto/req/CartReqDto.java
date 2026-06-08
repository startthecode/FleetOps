package org.samtar.warehouse.cart.dto.req;

import java.util.List;

public record CartReqDto(
    List<CartItemReqDto> items
) {

}
