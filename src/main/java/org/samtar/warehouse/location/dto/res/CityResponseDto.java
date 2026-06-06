package org.samtar.warehouse.location.dto.res;

import java.util.List;

public record CityResponseDto(
        long stateId,
        String stateName,
        @com.fasterxml.jackson.annotation.JsonUnwrapped
        LocationResDto country,
        List<LocationResDto> cities
) {
}
