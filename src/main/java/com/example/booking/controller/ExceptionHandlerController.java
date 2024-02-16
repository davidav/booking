package com.example.booking.controller;


import com.example.booking.dto.ErrorRs;
import com.example.booking.exception.BookingException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorRs> notFound(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorRs(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRs> notValid(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        String errorMessage = String.join("; ", errorMessages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorRs(errorMessage));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorRs> noAuthentication(AuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorRs(ex.getMessage()));
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<ErrorRs> noBooking(BookingException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorRs(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRs> unhandledErrors(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorRs(ex.getMessage()));
    }

}
