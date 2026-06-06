package org.samtar.warehouse.products.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.extern.java.Log;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateProductReqDto (
    @NotNull(message = "product id can not be blank")
    Long productID,

    @Size(max = 180, min = 5, message = "Product name must be between 5 to 180 character")
    String productName,

    @Size(max = 280, min = 5, message = "Product description must be between 5 to 280 character")
    String description,

    Double price,

    Double cost,

    int stockQuantity,

    int unit

) {
        public UpdateProductReqDto{
           if(productName != null) productName = productName.trim();
           if(description != null) description = description.trim();
        }
}
