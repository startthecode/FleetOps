package org.samtar.warehouse.location.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samtar.warehouse.location.dto.res.LocationResDto;
import org.samtar.warehouse.location.entity.CityEntity;
import org.samtar.warehouse.location.entity.CountryEntity;
import org.samtar.warehouse.location.entity.StateEntity;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "name", source = "countryName")
    @Mapping(target = "id", source = "countryId")
    LocationResDto singleItemToResponse(CountryEntity countryEntity);

    @Mapping(target = "name", source = "stateName")
    @Mapping(target = "id", source = "stateId")
    LocationResDto singleItemToResponse(StateEntity stateEntity);

    @Mapping(target = "name", source = "cityName")
    @Mapping(target = "id", source = "cityId")
    LocationResDto singleItemToResponse(CityEntity cityEntity);
}
