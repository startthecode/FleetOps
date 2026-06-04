package org.samtar.warehouse.common.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class ExceptionTemplate extends RuntimeException {
    String message;
    Object error;
    HttpStatus statusCode;

    public ExceptionTemplate(String message, Object error,
                             HttpStatus statusCode) {
        super(message);
        this.error = error;
        this.message = message;
        this.statusCode = statusCode;
    }
}
