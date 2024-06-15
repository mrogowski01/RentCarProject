package com.example.zti_project.exceptions;

public class InvalidReservationDateException extends RuntimeException {
    public InvalidReservationDateException(String message) {
        super(message);
    }
}
