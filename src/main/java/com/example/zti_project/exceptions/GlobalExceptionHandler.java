package com.example.zti_project.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidOfferDateException.class)
    public ResponseEntity<String> handleInvalidOfferDateException(InvalidOfferDateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(InvalidReservationDateException.class)
    public ResponseEntity<String> handleInvalidReservationDateException(InvalidReservationDateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<String> handleOfferNotFoundException(OfferNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
