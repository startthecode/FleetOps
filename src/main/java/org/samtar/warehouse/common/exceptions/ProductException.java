package org.samtar.warehouse.common.exceptions;

import org.springframework.http.HttpStatus;

public class ProductException extends ExceptionTemplate {
    public ProductException(String message,Object error,
                            HttpStatus statusCode) {
        super(message,error,statusCode);
    }


    public static ProductException alreadyExists(){
        return new ProductException("Product already exists",null,HttpStatus.CONFLICT);
    }

    public static ProductException notExists(Long productID){
        return new ProductException("Product with id " + productID + " doesn't exist",null,HttpStatus.NOT_FOUND);
    }

}
