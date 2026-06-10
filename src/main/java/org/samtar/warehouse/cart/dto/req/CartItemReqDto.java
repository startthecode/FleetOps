package org.samtar.warehouse.cart.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemReqDto(
        @NotNull(message = "Product id is required")
        Long productId,

        @NotNull(message = "Product quantity is required")
        @Min(value = 1, message = "Product quantity must be at least 1")
        @Max(value = 100, message = "Product quantity cannot exceed 100")
        Integer quantity,

        @NotNull(message = "Product price is required")
        @DecimalMin(value = "0.0", message = "Product price must be at least 0")
        Double price) {

}
