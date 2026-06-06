package org.samtar.warehouse.products.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductReqDto(

        @NotBlank(message = "Product name can not be blank")
        @Size(max = 180, min = 5, message = "Product name must be between 5 to 180 character")
        String productName,

        @NotBlank(message = "Product description can not be blank")
        @Size(max = 280, min = 5, message = "Product description must be between 5 to 280 character")
        String description,

        @NotNull(message = "Product Price can not be blank")
        Double price,

        @NotNull(message = "Product cost can not be blank")
        Double cost,

        @NotNull(message = "Product quantity can not be blank")
        int stockQuantity,

        @NotNull(message = "Product unit can not be blank")
        int unit

) {
        public CreateProductReqDto{
                productName = productName.trim();
                description = description.trim();
        }
}
