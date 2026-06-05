package org.samtar.warehouse.products.dto.res;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductResDto(
        Long productId,
        String productName,
        String description,
        Double price,
        Double cost,
        int stockQuantity,
        int unit,
        Long userId
) {

}
