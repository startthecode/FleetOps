package org.samtar.warehouse.warehouse.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateWareHouseReqDto(
        @NotBlank(message = "Warehouse  name can not be blank")
        @Size(max = 60, min = 3, message = "Warehouse name must be between 5 to 60 character")
        String warehouseName,

        @NotNull(message = "City id can be blank")
        Long cityId
) {
    public CreateWareHouseReqDto {
        if (warehouseName != null) {
            warehouseName = warehouseName.trim();
        }
    }
}
