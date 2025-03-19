package com.user_inventory_manager.user_service.advisor;

import com.user_inventory_manager.user_service.exception.AlreadyExistsException;
import com.user_inventory_manager.user_service.exception.InvalidOTPException;
import com.user_inventory_manager.user_service.exception.MisMatchException;
import com.user_inventory_manager.user_service.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AlreadyBoundException;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MisMatchException.class)
    public ResponseEntity<StandardResponse> handleMisMatchException(MisMatchException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new StandardResponse(403, "ERROR", ex.getMessage()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<StandardResponse> handleAlreadyExistsException(AlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).
                body(new StandardResponse(409, "ERROR", ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardResponse> handleUserNotFoundException(UsernameNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new StandardResponse(404, "NOT FOUND", ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardResponse> handleBadCredentialsException(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new StandardResponse(404, "ERROR", ex.getMessage()));
    }

    @ExceptionHandler(InvalidOTPException.class)
    public ResponseEntity<StandardResponse> handleBadCredentialsException(InvalidOTPException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new StandardResponse(404, "ERROR", ex.getMessage()));
    }

}
