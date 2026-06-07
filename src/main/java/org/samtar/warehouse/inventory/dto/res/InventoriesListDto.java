package org.samtar.warehouse.inventory.dto.res;

import java.util.List;

public record InventoriesListDto(
        String type,
        List<InventoryItemDto> storages
){

}
