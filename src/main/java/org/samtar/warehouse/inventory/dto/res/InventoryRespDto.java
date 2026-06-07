package org.samtar.warehouse.inventory.dto.res;

import java.util.List;

public record InventoryRespDto(
        String productName,
        List<InventoriesListDto> inventories
) {
}
