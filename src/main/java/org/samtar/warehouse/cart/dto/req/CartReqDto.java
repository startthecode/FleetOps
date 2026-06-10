package org.samtar.warehouse.cart.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CartReqDto(
    @NotEmpty(message = "Cart items are required")
    List<@Valid CartItemReqDto> items
) {

}
