package org.samtar.warehouse.orders.dto.res;

import java.util.Set;

import org.samtar.warehouse.common.enums.OrderStatus;

public record OrderResDto(
    Long orderid,
    Set<OrderItemResDto> items,
    OrderStatus orderStatus
) {
}
