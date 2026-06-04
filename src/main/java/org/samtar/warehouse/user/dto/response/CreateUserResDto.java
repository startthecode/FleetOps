package org.samtar.warehouse.user.dto.response;

import org.samtar.warehouse.common.dto.JwtTokenDto;
import org.samtar.warehouse.common.enums.Roles;

public record CreateUserResDto(
        Long userid,
        Roles role,
        JwtTokenDto tokens
) {

}
