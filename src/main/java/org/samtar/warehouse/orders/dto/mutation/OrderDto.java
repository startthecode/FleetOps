package org.samtar.warehouse.orders.dto.mutation;

import java.util.List;

public record OrderDto(
    List<OrderItemDto> items
) {

}
