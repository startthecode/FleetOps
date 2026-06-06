package org.samtar.warehouse.common.exceptions;

import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponseDto<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(new GenericResponseDto<>(
                        "Validation failed",
                        false,
                        errors
                ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDto<Object>> genericException(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDto<>(exception.getMessage(),false,null));
    }

}
