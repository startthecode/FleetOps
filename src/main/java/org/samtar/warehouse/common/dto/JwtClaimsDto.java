package org.samtar.warehouse.common.dto;

public record JwtClaimsDto(
    String username,
    String email
) {
    
}
