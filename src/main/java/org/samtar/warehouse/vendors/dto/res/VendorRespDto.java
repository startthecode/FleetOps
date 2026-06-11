package org.samtar.warehouse.vendors.dto.res;

import org.samtar.warehouse.common.enums.Roles;

public record VendorRespDto(
        Long id,
        String username,
        String email,
        String phone,
        Roles role,
        Integer heightInInch,
        Integer bodyWeight,
        Boolean haveDisease
) {
}
