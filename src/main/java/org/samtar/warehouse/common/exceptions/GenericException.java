package org.samtar.warehouse.common.exceptions;

import org.springframework.http.HttpStatus;

public class GenericException extends ExceptionTemplate{

    public GenericException(String message, Object error, HttpStatus statusCode) {
        super(message, error, statusCode);
    }

    public static GenericException exceptionGeneric(String message,Object data,HttpStatus httpStatus){
       return new GenericException(message,data,httpStatus);
    }
    public static GenericException exceptionGeneric(){
        return new GenericException("something went wrong",null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
