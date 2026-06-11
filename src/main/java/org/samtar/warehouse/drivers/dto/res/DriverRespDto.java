package org.samtar.warehouse.drivers.dto.res;

import org.samtar.warehouse.common.enums.Roles;

public record DriverRespDto(
        Long id,
        String username,
        String email,
        String phone,
        Roles role,
        Integer heightInInch,
        Integer bodyWeight,
        Boolean haveDisease,
        Integer driveRating,
        Boolean haveDrivingLicence
) {
}
