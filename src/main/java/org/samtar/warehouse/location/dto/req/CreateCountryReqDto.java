package org.samtar.warehouse.location.dto.req;

public record CreateCountryReqDto(
        String countryName
) {
    public CreateCountryReqDto {
        if(countryName != null){
            countryName = countryName.trim();
        }
    }
}
