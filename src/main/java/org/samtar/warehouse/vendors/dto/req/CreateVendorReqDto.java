package org.samtar.warehouse.vendors.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateVendorReqDto(
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
        @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits")
        String phone,

        Long cityID,

        @NotNull(message = "Height is required")
        @Max(value = 10, message = "Height must be at most 10")
        Integer heightInInch,

        @NotNull(message = "Body weight is required")
        @Min(value = 1, message = "Body weight must be at least 1")
        @Max(value = 10, message = "Body weight must be at most 10")
        Integer bodyWeight,

        @NotNull(message = "haveDisease is required")
        Boolean haveDisease
) {
}
