package org.samtar.warehouse.warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samtar.warehouse.warehouse.dto.res.WarehouseResDto;
import org.samtar.warehouse.warehouse.entity.WarehouseEntity;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    @Mapping(target = "city", source = "city.cityName")
    WarehouseResDto toResponse(WarehouseEntity warehouseEntity);

    @Mapping(target = "city", source = "city.cityName")
    void forUpdate(WarehouseEntity warehouseEntity);
}
