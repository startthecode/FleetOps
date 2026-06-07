package org.samtar.warehouse.common.exceptions;

import org.samtar.warehouse.common.enums.LocationTree;
import org.springframework.http.HttpStatus;

public class LocationException extends ExceptionTemplate{
    public LocationException(String message, Object error, HttpStatus statusCode) {
        super(message, error, statusCode);
    }

    public static LocationException alreadyExists(LocationTree locationTree,String locationName){
        return new LocationException(locationTree + " is Already Exists with name "+locationName,null,HttpStatus.CONFLICT);
    }

    public static LocationException notExists(LocationTree locationTree,long locationID){
        return new LocationException(locationTree + " is Not Exists with name "+locationID,null,HttpStatus.CONFLICT);
    }


}
