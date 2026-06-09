package org.samtar.warehouse.common.exceptions;

import org.springframework.http.HttpStatus;

public class OrderException extends ExceptionTemplate {

    public OrderException(String message, Object error, HttpStatus statusCode) {
        super(message, error, statusCode);
    }

    public static OrderException cartIsEmpty() {

        return new OrderException("Your cart is empty", null, HttpStatus.BAD_REQUEST);

    }

    public static OrderException orderNotfound() {

        return new OrderException("Order Not found", null, HttpStatus.NOT_FOUND);

    }

}
