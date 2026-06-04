package org.samtar.warehouse.common.dto.response;

public record GenericResponseDto<T>(
        String message,
        Boolean success,
        T data

) {
}
