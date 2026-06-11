package org.samtar.warehouse.common.exceptions;

import org.samtar.warehouse.common.enums.TokenTypes;
import org.springframework.http.HttpStatus;

public class AuthException extends ExceptionTemplate {
    public AuthException(String message,Object error,HttpStatus statusCode) {
        super(message,error,statusCode);
    }

    public static AuthException InvalidCredentials(Object error){
        return new AuthException( "Invalid Credentials",   error,HttpStatus.BAD_REQUEST);
    }

    public static AuthException UserNotFound(String message ){
        return new AuthException( message,null, HttpStatus.NOT_FOUND);
    }

    public static AuthException userAlreadyExists(String message ){
        return new AuthException( message, null, HttpStatus.CONFLICT);
    }

    public static AuthException tokenExpired(TokenTypes type){
        return new AuthException( type + " token has been expired",  null,HttpStatus.BAD_GATEWAY);
    }
    public static AuthException InvalidToken(TokenTypes type){
        return new AuthException( type +  " token is invalid",null,  HttpStatus.BAD_GATEWAY);
    }

    public static  AuthException sessionExpired() {
        return new AuthException("Session expired. Please login again",null,HttpStatus.BAD_GATEWAY);
    }

    public static  AuthException missingAuthHeaders() {
        return new AuthException("Missing or malformed Authorization header",null,HttpStatus.UNAUTHORIZED);
    }
    public static  AuthException accountBlocked() {
        return new AuthException("Account is blocked",null,HttpStatus.UNAUTHORIZED);
    }

    public static  AuthException unAuthorized() {
        return new AuthException("You dont have a permission to use this",false,HttpStatus.UNAUTHORIZED);
    }
}
