package org.samtar.warehouse.common.exceptions;

import org.samtar.warehouse.common.enums.LocationTree;
import org.springframework.http.HttpStatus;

public class WarehouseException extends  ExceptionTemplate{
    public WarehouseException(String message, Object error, HttpStatus statusCode) {
        super(message, error, statusCode);
    }

    public static WarehouseException alreadyExists(String warehouseName){
        return new WarehouseException("Warehouse is Already Exists with name"+warehouseName,null,HttpStatus.CONFLICT);
    }
    public static WarehouseException alreadyExists(Long warehouseId){
        return new WarehouseException("Warehouse is Already Exists with id"+warehouseId,null,HttpStatus.CONFLICT);
    }

    public static WarehouseException notExists(long warehouseName){
        return new WarehouseException("Warehouse is Already Exists with name "+warehouseName,null,HttpStatus.CONFLICT);
    }

    public static WarehouseException notExists(String warehouseId){
        return new WarehouseException("Warehouse is Already Exists with id "+warehouseId,null,HttpStatus.CONFLICT);
    }

}
