package org.samtar.warehouse.common.exceptions;

import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException extends RuntimeException {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<GenericResponseDto<Object>> autheException(AuthException exception){
        return ResponseEntity.status(exception.statusCode).body(new GenericResponseDto<>(exception.message,false,exception.error));
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<GenericResponseDto<Object>> genericException(GenericException exception){
        return ResponseEntity.status(exception.statusCode).body(new GenericResponseDto<>(exception.message,false,exception.error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDto<Object>> genericException(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDto<>(exception.getMessage(),false,null));
    }

}
