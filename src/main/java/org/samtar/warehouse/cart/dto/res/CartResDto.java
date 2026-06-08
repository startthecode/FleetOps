package org.samtar.warehouse.cart.dto.res;

import java.util.Set;

public record CartResDto(
    Set<CartItemResDto> items
) {



}
