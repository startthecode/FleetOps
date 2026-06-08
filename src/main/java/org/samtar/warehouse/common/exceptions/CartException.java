package org.samtar.warehouse.common.exceptions;

import org.springframework.http.HttpStatus;

public class CartException extends ExceptionTemplate {
    public CartException(String message, Object error, HttpStatus statusCode) {
        super(message, error, statusCode);
    }

    public static CartException EmptyCart(){
        return new CartException("Your cart is empty",null,HttpStatus.CONFLICT);
    }

}
