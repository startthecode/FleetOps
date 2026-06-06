package org.samtar.warehouse.location.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateReqDto (
        @NotNull(message = "Id can not be blank")
        Long id,

        @NotBlank(message = "Name can not be blank")
        String newName
){
}
