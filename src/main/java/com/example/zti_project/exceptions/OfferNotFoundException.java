package com.example.zti_project.exceptions;


public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(String message) {
        super(message);
    }
}