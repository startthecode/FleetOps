package org.samtar.warehouse.location.dto.res;
import java.util.List;

public record StateResponseDto(
        long countryId,
        String countryName,
        List<LocationResDto> states
) {
}
