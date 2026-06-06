package org.samtar.warehouse.warehouse.dto.res;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WarehouseResDto(
        long warehouseId,
        String warehouseName,
        String city
) {
}
