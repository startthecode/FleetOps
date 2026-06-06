package org.samtar.warehouse.location.dto.req;

public record CreateCityReqDto(
        Long stateID,
        String cityName
) {

}
