package com.payment_tracker;

import com.payment_tracker.customeexception.PaymentConflictException;
import com.payment_tracker.customeexception.PaymentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StandaloneTrackerExceptionHandler {
    //409-Conflict exception
    @ExceptionHandler(PaymentConflictException.class)
    public ResponseEntity<ErrorResponseCode> conflictErrorHandle(PaymentConflictException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseCode("CONFLICT", ex.getMessage()));
    }

    //404 -> NOT_FOUND
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponseCode> handleNotFound(PaymentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseCode("NOT_FOUND", ex.getMessage()));
    }





}
