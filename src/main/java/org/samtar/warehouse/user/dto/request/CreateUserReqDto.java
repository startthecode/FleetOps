package org.samtar.warehouse.user.dto.request;

import jakarta.validation.constraints.*;
import org.samtar.warehouse.common.enums.Roles;
import org.springframework.web.bind.MethodArgumentNotValidException;

public record CreateUserReqDto(
        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Phone number must contain exactly 10 digits"
        )
        String phone,

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,

        boolean isVendorAccount,
        Long cityID

) {
    @AssertTrue(message = "State defining is mandatory for vendors")
    public boolean vendorValidation() {
        return isVendorAccount && (cityID != null);
    }
}
