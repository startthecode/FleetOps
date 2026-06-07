package org.samtar.warehouse.inventory.mapper;

import org.mapstruct.Mapper;
import org.samtar.warehouse.inventory.dto.res.InventoryRespDto;
import org.samtar.warehouse.inventory.entity.InventoryEntity;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryRespDto toResponse(InventoryEntity inventoryEntity);

}
