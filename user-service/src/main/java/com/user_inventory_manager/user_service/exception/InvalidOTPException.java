package com.user_inventory_manager.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOTPException extends RuntimeException{
    public InvalidOTPException(String message){
        super(message);
    }
}
