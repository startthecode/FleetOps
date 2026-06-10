package org.samtar.warehouse.cart.dto.res;

import java.util.List;

public record CartResDto(
    List<CartItemResDto> items
) {



}
