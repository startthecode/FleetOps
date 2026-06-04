package org.samtar.warehouse.common.dto;

import java.util.Map;

public record JwtTokenDto(
   String accessToken,
   String refreshToken
) {

}
