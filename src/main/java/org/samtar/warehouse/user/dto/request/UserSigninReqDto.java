package org.samtar.warehouse.user.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserSigninReqDto (
        @NotBlank(message = "Username can not black")
        @Size(min = 5,max = 30)
        String username,

        @NotBlank
        @Size(min = 8,max = 80)
        String password
){
}
