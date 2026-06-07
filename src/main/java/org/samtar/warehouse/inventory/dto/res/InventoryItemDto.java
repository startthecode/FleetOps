package org.samtar.warehouse.inventory.dto.res;

public record InventoryItemDto(
        Long id,
        String name,
        Integer stock
) {
}
