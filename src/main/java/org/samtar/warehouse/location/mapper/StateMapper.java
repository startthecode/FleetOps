package org.samtar.warehouse.location.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samtar.warehouse.location.dto.res.StateResponseDto;
import org.samtar.warehouse.location.entity.CountryEntity;
import org.samtar.warehouse.location.entity.StateEntity;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface StateMapper {
    @Mapping(target = "countryName", source = "countryName")
    @Mapping(target = "countryId", source = "countryId")
    StateResponseDto toResponse(CountryEntity countryEntity);
}
