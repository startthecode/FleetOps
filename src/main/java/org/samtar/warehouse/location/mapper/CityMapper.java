package org.samtar.warehouse.location.mapper;

import org.mapstruct.Mapper;
import org.samtar.warehouse.location.dto.res.CityResponseDto;
import org.samtar.warehouse.location.entity.StateEntity;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface CityMapper
{
    CityResponseDto toResponse(StateEntity stateEntity);
}
