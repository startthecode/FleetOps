package org.samtar.warehouse.common.exceptions;

import org.springframework.http.HttpStatus;

public class InventoryException extends ExceptionTemplate {

    public InventoryException(String message, Object error, HttpStatus statusCode) {
        super(message, error, statusCode);
    }

    public static InventoryException alreadyExists(Long inventoryId){
        return new InventoryException("Inventory is Already Exists with id"+inventoryId,null,HttpStatus.CONFLICT);
    }

    public static InventoryException notExists(Long inventoryId){
        return new InventoryException("Inventory is not Exists with id "+inventoryId,null,HttpStatus.CONFLICT);
    }

    public static InventoryException lastItemDeleteNotAllowed(){
        return new InventoryException("Product have to maintain at least on inventory.",null,HttpStatus.CONFLICT);
    }


}
