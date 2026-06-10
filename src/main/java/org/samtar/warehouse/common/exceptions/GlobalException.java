package org.samtar.warehouse.common.exceptions;

import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
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

        ex.getBindingResult()
                .getGlobalErrors()
                .forEach(error ->
                        errors.put(error.getObjectName(), error.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(new GenericResponseDto<>(
                        "Validation failed",
                        false,
                        errors
                ));
    }

    @ExceptionHandler({DataIntegrityViolationException.class, TransactionSystemException.class})
    public ResponseEntity<GenericResponseDto<Object>> databaseException(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new GenericResponseDto<>(
                        getRootCauseMessage(exception),
                        false,
                        null
                ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDto<Object>> genericException(Exception exception){
        exception.printStackTrace(); // ← add this line
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDto<>(getExceptionMessage(exception),false,null));
    }

    private String getRootCauseMessage(Throwable exception) {
        Throwable rootCause = exception;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return getExceptionMessage(rootCause);
    }

    private String getExceptionMessage(Throwable exception) {
        return exception.getMessage() != null
                ? exception.getMessage()
                : "Unexpected error: " + exception.getClass().getSimpleName();
    }

}
